package expenseincome.expense;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Currency;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.ExpenseDataManager;
import cashflow.model.interfaces.Finance;
import cashflow.model.FinanceData;
import cashflow.model.storage.Storage;
import utils.money.Money;
import expenseincome.expense.exceptions.ExpenseException;

/**
 * Manages all expense-related operations including adding, editing, deleting,
 * listing, sorting, and analyzing expenses.
 * <p>
 * Integrates with {@link FinanceData} and {@link BudgetManager} to enable
 * budget-aware expense tracking.
 */
public class ExpenseManager implements ExpenseDataManager {
    private static final Logger logger = Logger.getLogger(ExpenseManager.class.getName());

    static {
        try {
            // Create logs directory if it doesn't exist
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                boolean created = logDir.mkdirs();
                if (!created) {
                    System.err.println("Failed to create log directory.");
                }
            }

            // Remove default console handlers
            Logger rootLogger = Logger.getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            // Set up file handler to log into logs/expense.log
            FileHandler fileHandler = new FileHandler("logs/expense.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Failed to initialize log file handler: " + e.getMessage());
        }
    }

    private final ArrayList<Expense> expenses;
    private final FinanceData data;
    private final String currency;
    private Storage expenseStorage;

    /**
     * Constructs a new ExpenseManager.
     *
     * @param data     FinanceData instance for accessing currency and budget
     * @param currency User's preferred currency code (e.g. "USD")
     */
    public ExpenseManager(FinanceData data, String currency, Storage expenseStorage) throws FileNotFoundException {
        this.data = data;
        assert currency != null && !currency.isEmpty() : "Currency must not be null or empty.";
        this.currency = currency;
        this.expenseStorage = expenseStorage;
        this.expenses = new ArrayList<Expense>();
        ArrayList<Finance> loadedFile = expenseStorage.loadFile();
        if (loadedFile != null) {
            for (Finance f : loadedFile) {
                if (f instanceof Expense) {
                    expenses.add((Expense) f);
                }
            }
        }
    }

    /**
     * Returns a defensive copy of the list of expenses as Finance objects.
     */
    public ArrayList<Finance> getExpenseList() {
        return new ArrayList<>(expenses);
    }

    /**
     * Returns the actual internal list of Expense objects.
     */
    public ArrayList<Expense> getList() {
        return expenses;
    }

    /**
     * Returns the number of expense entries recorded.
     */
    public int getExpenseCount() {
        return expenses.size();
    }

    /**
     * Retrieves an expense at the given index (0-based).
     *
     * @param i the index to retrieve
     * @return the corresponding Expense object
     */
    public Expense getExpense(int i) {
        return expenses.get(i);
    }

    /**
     * Adds a new expense after validating all fields. Updates budget if linked.
     *
     * @param description the description of the expense
     * @param amount      the monetary amount
     * @param date        the date of the expense
     * @param category    the expense category
     */
    public void addExpense(String description, double amount, LocalDate date, String category) {
        try {
            validateExpenseDetails(description, amount, category);
            Currency currency = data.getCurrency();
            Money money = new Money(currency, amount);
            Expense expense = new Expense(description, money, date, category);
            expenses.add(expense);
            expenseStorage.saveFile(new ArrayList<>(expenses));

            BudgetManager budgetManager = data.getBudgetManager();
            if (budgetManager != null) {
                boolean exceeded = budgetManager.deductBudgetFromExpense(expense);
                if (exceeded) {
                    System.out.println("Warning: You have exceeded your budget for category: " + category);
                }
            }

            logger.log(Level.INFO, "Added expense: {0}", expense);
            System.out.println("Added: " + expense);

        } catch (ExpenseException e) {
            logger.log(Level.WARNING, "Failed to add expense", e);
            System.out.println("Failed to add expense. " + e.getMessage());
        }
    }

    /**
     * Deletes an expense at a given index (1-based).
     *
     * @param index the index (1-based) to delete
     */
    public void deleteExpense(int index) {
        try {
            validateIndex(index);
            Expense removed = expenses.remove(index - 1);
            logger.log(Level.INFO, "Deleted expense: {0}", removed);
            System.out.println("Deleted: " + removed);
        } catch (ExpenseException e) {
            logger.log(Level.WARNING, "Failed to delete expense at index: " + index, e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Edits an existing expense entry.
     *
     * @param index         the index (1-based) to edit
     * @param newDescription new description
     * @param newAmount      new amount
     * @param newDate        new date
     * @param newCategory    new category
     */
    public void editExpense(int index, String newDescription, double newAmount,
                            LocalDate newDate, String newCategory) {
        try {
            validateIndex(index);
            validateExpenseDetails(newDescription, newAmount, newCategory);

            Expense expense = expenses.get(index - 1);
            Currency currency = data.getCurrency();
            Money money = new Money(currency, newAmount);

            expense.setDescription(newDescription);
            expense.setAmount(money);
            expense.setDate(newDate);
            expense.setCategory(newCategory);

            BudgetManager budgetManager = data.getBudgetManager();
            if (budgetManager != null) {
                boolean exceeded = budgetManager.deductBudgetFromExpense(expense);
                if (exceeded) {
                    System.out.println("Warning: You have exceeded your budget for category: " + newCategory);
                }
            }

            logger.log(Level.INFO, "Updated expense: {0}", expense);
            System.out.println("Updated: " + expense);
        } catch (ExpenseException e) {
            logger.log(Level.WARNING, "Failed to edit expense", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints a list of all recorded expenses.
     */
    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("Expense List:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    /**
     * Lists expenses filtered by the given category.
     *
     * @param category the category to filter by
     */
    public void listExpensesByCategory(String category) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("Expenses in category: " + category);
        boolean found = false;

        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            if (e.getCategory().equalsIgnoreCase(category)) {
                System.out.println((i + 1) + ". " + e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }

    /**
     * Sorts expenses by date.
     *
     * @param mostRecentFirst true for descending (recent first), false for ascending
     */
    public void sortExpensesByDate(boolean mostRecentFirst) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to sort.");
            return;
        }

        expenses.sort((e1, e2) -> mostRecentFirst
                ? e2.getDate().compareTo(e1.getDate())
                : e1.getDate().compareTo(e2.getDate()));

        System.out.println("Expenses sorted by " + (mostRecentFirst ? "most recent" : "oldest") + " first:");
        listExpenses();
    }

    /**
     * Prints the category with the highest total expense.
     */
    public void printTopCategory() {
        printCategorySummary(true);
    }

    /**
     * Prints the category with the lowest total expense.
     */
    public void printBottomCategory() {
        printCategorySummary(false);
    }

    /**
     * Prints summary of total spending per category.
     *
     * @param top if true, prints top category; otherwise prints bottom category
     */
    private void printCategorySummary(boolean top) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        Map<String, BigDecimal> totals = new HashMap<>();
        for (Expense e : expenses) {
            String category = e.getCategory();
            BigDecimal amount = BigDecimal.valueOf(e.getAmount());
            totals.put(category, totals.getOrDefault(category, BigDecimal.ZERO).add(amount));
        }

        String targetCategory = null;
        BigDecimal targetAmount = null;

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            if (targetAmount == null ||
                    (top && entry.getValue().compareTo(targetAmount) > 0) ||
                    (!top && entry.getValue().compareTo(targetAmount) < 0)) {
                targetAmount = entry.getValue();
                targetCategory = entry.getKey();
            }
        }

        if (targetCategory != null) {
            System.out.printf("%s Spending Category: %s ($%.2f)%n",
                    top ? "Top" : "Lowest", targetCategory, targetAmount);
        }
    }

    /**
     * Validates the given index is within the range of the expense list.
     *
     * @param index index to validate (1-based)
     * @throws ExpenseException if index is invalid or list is empty
     */
    private void validateIndex(int index) throws ExpenseException {
        if (expenses.isEmpty()) {
            throw new ExpenseException("No expenses to delete. Your list is empty.");
        }
        if (index < 1 || index > expenses.size()) {
            throw new ExpenseException("Invalid index: must be between 1 and " + expenses.size());
        }
    }

    /**
     * Validates the fields for an expense before adding or editing.
     *
     * @param description the description of the expense
     * @param amount      the amount
     * @param category    the category
     * @throws ExpenseException if any field is invalid
     */
    private void validateExpenseDetails(String description, double amount, String category)
            throws ExpenseException {
        if (description == null || description.trim().isEmpty()) {
            throw new ExpenseException("Description cannot be empty.");
        }
        if (amount <= 0) {
            throw new ExpenseException("Amount must be greater than zero.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new ExpenseException("Category cannot be empty.");
        }
    }

    /**
     * Returns the category with the highest total spending.
     *
     * @return name of the top category
     */
    @Override
    public String getTopCategory() {
        if (expenses.isEmpty()) {
            throw new IllegalStateException("No expenses recorded.");
        }

        Map<String, BigDecimal> totals = new HashMap<>();
        for (Expense e : expenses) {
            String category = e.getCategory();
            BigDecimal amount = BigDecimal.valueOf(e.getAmount());
            totals.put(category, totals.getOrDefault(category, BigDecimal.ZERO).add(amount));
        }

        String topCategory = null;
        BigDecimal maxAmount = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            if (entry.getValue().compareTo(maxAmount) > 0) {
                maxAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        if (topCategory == null) {
            throw new IllegalStateException("No top category found.");
        }

        return topCategory;
    }
}

