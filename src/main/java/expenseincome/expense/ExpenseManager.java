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
            FileHandler fileHandler = new FileHandler("logs/expense.log", true); // append mode
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Failed to initialize log file handler: " + e.getMessage());
        }
    }


    private final ArrayList<Expense> expenses;
    private final FinanceData data;
    private String currency;
    private Storage expenseStorage;

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

    public ArrayList<Finance> getExpenseList() {
        return new ArrayList<>(expenses);
    }

    public ArrayList<Expense> getList() {
        return expenses;
    }

    public int getExpenseCount() {
        return expenses.size();
    }

    public Expense getExpense(int i) {
        return expenses.get(i);
    }

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

            logger.log(Level.INFO, "Updated expense: {0}", expense);
            System.out.println("Updated: " + expense);
        } catch (ExpenseException e) {
            logger.log(Level.WARNING, "Failed to edit expense", e);
            System.out.println(e.getMessage());
        }
    }

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

    public void printTopCategory() {
        printCategorySummary(true);
    }

    public void printBottomCategory() {
        printCategorySummary(false);
    }

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

    private void validateIndex(int index) throws ExpenseException {
        if (expenses.isEmpty()) {
            throw new ExpenseException("No expenses to delete. Your list is empty.");
        }
        if (index < 1 || index > expenses.size()) {
            throw new ExpenseException("Invalid index: must be between 1 and " + expenses.size());
        }
    }

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
