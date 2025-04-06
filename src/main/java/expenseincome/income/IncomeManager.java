package expenseincome.income;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import cashflow.model.interfaces.IncomeDataManager;
import expenseincome.income.exceptions.IncomeException;
import utils.money.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Currency;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.Map;
import java.util.HashMap;
/**
 * Handles business logic for income entries. Supports add, edit, delete, list, sort, and analytics.
 */
public class IncomeManager implements IncomeDataManager {
    private static final Logger logger = Logger.getLogger(IncomeManager.class.getName());
    private final ArrayList<Income> incomes;
    private final FinanceData data;
    private final String currency;

    static {
        try {
            // Ensure logs directory exists
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

            // Set up file handler for logging
            FileHandler fileHandler = new FileHandler("logs/income.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.ALL);

        } catch (Exception e) {
            System.err.println("Failed to initialize logging: " + e.getMessage());
        }
    }
    /**
     * Constructs an IncomeManager.
     *
     * @param data     The FinanceData model containing shared application state.
     * @param currency The preferred currency code (e.g., USD, SGD).
     */
    public IncomeManager(FinanceData data, String currency) {
        assert currency != null && !currency.isEmpty() : "Currency must not be null or empty.";
        this.incomes = new ArrayList<>();
        this.data = data;
        this.currency = currency;
    }

    public ArrayList<Finance> getIncomeList() {
        return new ArrayList<>(incomes);
    }

    public int getIncomeCount() {
        return incomes.size();
    }

    public Income getIncome(int i) {
        return incomes.get(i);
    }
    /**
     * Adds a new income entry to the list.
     *
     * @param source   The source of income.
     * @param amount   The monetary value.
     * @param date     The date received.
     * @param category The income category.
     */
    public void addIncome(String source, double amount, LocalDate date, String category) {
        try {
            validateIncomeDetails(source, amount, category);
            Currency currency = data.getCurrency();
            Money money = new Money(currency, amount);
            Income income = new Income(source, money, date, category);
            incomes.add(income);
            logger.log(Level.INFO, "Added income: {0}", income);
            System.out.println("Added: " + income);
        } catch (IncomeException e) {
            logger.log(Level.WARNING, "Failed to add income", e);
            System.out.println("Failed to add income. " + e.getMessage());
        }
    }
    /**
     * Edits an existing income entry.
     *
     * @param index        Index of income in list (1-based).
     * @param newSource    New source value.
     * @param newAmount    New amount.
     * @param newDate      New date.
     * @param newCategory  New category.
     */
    public void editIncome(int index, String newSource, double newAmount, LocalDate newDate, String newCategory) {
        try {
            validateIndex(index);
            validateIncomeDetails(newSource, newAmount, newCategory);
            Income income = incomes.get(index - 1);
            Currency currency = data.getCurrency();
            Money money = new Money(currency, newAmount);
            income.setSource(newSource);
            income.setAmount(money);
            income.setDate(newDate);
            income.setCategory(newCategory);
            logger.log(Level.INFO, "Updated income: {0}", income);
            System.out.println("Updated: " + income);
        } catch (IncomeException e) {
            logger.log(Level.WARNING, "Failed to edit income", e);
            System.out.println("Failed to edit income. " + e.getMessage());
        }
    }
    /**
     * Deletes an income entry.
     *
     * @param index Index of income in list (1-based).
     */
    public void deleteIncome(int index) {
        try {
            validateIndex(index);
            Income removed = incomes.remove(index - 1);
            logger.log(Level.INFO, "Deleted income: {0}", removed);
            System.out.println("Deleted: " + removed);
        } catch (IncomeException e) {
            logger.log(Level.WARNING, "Failed to delete income", e);
            System.out.println(e.getMessage());
        }
    }
    /**
     * Lists all income entries to stdout.
     */
    public void listIncomes() {
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded.");
            return;
        }
        System.out.println("Income List:");
        for (int i = 0; i < incomes.size(); i++) {
            System.out.println((i + 1) + ". " + incomes.get(i));
        }
    }
    /**
     * Lists incomes by matching category.
     *
     * @param category Category filter (case-insensitive).
     */
    public void listIncomesByCategory(String category) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded.");
            return;
        }

        boolean found = false;
        System.out.println("Incomes in category: " + category);
        for (int i = 0; i < incomes.size(); i++) {
            Income income = incomes.get(i);
            if (income.getCategory().equalsIgnoreCase(category)) {
                System.out.println((i + 1) + ". " + income);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No incomes found in this category.");
        }
    }
    /**
     * Sorts incomes by date, either most recent first or oldest first.
     *
     * @param mostRecentFirst True for descending order.
     */
    public void sortIncomesByDate(boolean mostRecentFirst) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes to sort.");
            return;
        }

        incomes.sort((i1, i2) -> mostRecentFirst ? i2.getDate().compareTo(i1.getDate())
                : i1.getDate().compareTo(i2.getDate()));

        listIncomes();
    }
    /**
     * Prints the category with the highest total income.
     */
    public void printTopCategory() {
        printCategorySummary(true);
    }
    /**
     * Prints the category with the lowest total income.
     */
    public void printBottomCategory() {
        printCategorySummary(false);
    }
    /**
     * Helper function to compute and print category summary.
     *
     * @param top True for max, false for min.
     */
    private void printCategorySummary(boolean top) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded.");
            return;
        }

        Map<String, BigDecimal> totals = new HashMap<>();
        for (Income income : incomes) {
            String category = income.getCategory();
            BigDecimal amount = BigDecimal.valueOf(income.getAmount());
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
            System.out.printf("%s Income Category: %s ($%.2f)%n",
                    top ? "Top" : "Lowest", targetCategory, targetAmount);
        }
    }

    private void validateIndex(int index) throws IncomeException {
        if (incomes.isEmpty()) {
            throw new IncomeException("No incomes to delete. Your list is empty.");
        }
        if (index < 1 || index > incomes.size()) {
            throw new IncomeException("Invalid index: must be between 1 and " + incomes.size());
        }
    }

    private void validateIncomeDetails(String source, double amount, String category) throws IncomeException {
        if (source == null || source.trim().isEmpty()) {
            throw new IncomeException("Source cannot be empty.");
        }
        if (amount <= 0) {
            throw new IncomeException("Amount must be greater than zero.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IncomeException("Category cannot be empty.");
        }
    }
}
