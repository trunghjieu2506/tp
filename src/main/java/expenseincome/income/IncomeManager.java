package expenseincome.income;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import cashflow.model.interfaces.IncomeDataManager;
import utils.money.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Currency;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;


public class IncomeManager implements IncomeDataManager {
    private static final Logger logger = Logger.getLogger(IncomeManager.class.getName());
    private ArrayList<Income> incomes;
    private FinanceData data;
    private String currency;

    public ArrayList<Finance> getIncomeList() {
        return new ArrayList<>(incomes);
    }

    public IncomeManager(FinanceData data, String currency) {
        assert currency != null && !currency.isEmpty() : "Currency must not be null or empty.";
        this.incomes = new ArrayList<>();
        this.data = data;
        this.currency = currency;
    }

    public void addIncome(String source, double amount, LocalDate date, String category) {
        try {
            if (source == null || source.trim().isEmpty()) {
                throw new IllegalArgumentException("Income source cannot be empty.");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Income amount must be greater than zero.");
            }
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalArgumentException("Income category cannot be empty.");
            }

            Currency currency = data.getCurrency();
            Money money = new Money(currency, amount);
            Income income = new Income(source, money, date, category);
            incomes.add(income);
            logger.log(Level.INFO, "Added income: {0}", income);
            System.out.println("Added: " + income);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Failed to add income", e);
            System.out.println("Failed to add income. " + e.getMessage());
        }
    }

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

    public void deleteIncome(int index) {
        try {
            logger.log(Level.INFO, "Attempting to delete income at index: {0}", index);

            if (index < 1 || index > incomes.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + incomes.size());
            }

            Income removed = incomes.remove(index - 1);
            logger.log(Level.INFO, "Deleted income: {0}", removed);
            System.out.println("Deleted: " + removed);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Failed to delete income at index: " + index, e);
            System.out.println("Failed to delete income. " + e.getMessage());
        }
    }

    public void editIncome(int index, String newSource, double newAmount, LocalDate newDate, String newCategory) {
        try {
            logger.log(Level.INFO, "Editing income at index {0} to new values: {1}, ${2}, {3}, category={4}",
                    new Object[]{index, newSource, newAmount, newDate, newCategory});

            if (index < 1 || index > incomes.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + incomes.size());
            }
            if (newSource == null || newSource.trim().isEmpty()) {
                throw new IllegalArgumentException("New source cannot be empty.");
            }
            if (newAmount <= 0) {
                throw new IllegalArgumentException("New amount must be greater than zero.");
            }
            if (newCategory == null || newCategory.trim().isEmpty()) {
                throw new IllegalArgumentException("New category cannot be empty.");
            }

            Income income = incomes.get(index - 1);
            Currency currency = data.getCurrency();
            Money money = new Money(currency, newAmount);
            income.setSource(newSource);
            income.setAmount(money);
            income.setDate(newDate);
            income.setCategory(newCategory);

            logger.log(Level.INFO, "Updated income: {0}", income);
            System.out.println("Updated: " + income);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Failed to edit income at index: " + index, e);
            System.out.println("Failed to edit income. " + e.getMessage());
        }
    }

    public void sortIncomesByDate(boolean mostRecentFirst) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes to sort.");
            return;
        }

        logger.log(Level.INFO, "Sorting incomes by date. Order: {0}",
                mostRecentFirst ? "most recent first" : "oldest first");

        incomes.sort((i1, i2) -> {
            if (mostRecentFirst) {
                return i2.getDate().compareTo(i1.getDate());
            } else {
                return i1.getDate().compareTo(i2.getDate());
            }
        });

        System.out.println("Incomes sorted by " + (mostRecentFirst ? "most recent" : "oldest") + " first.");
        listIncomes();
    }

    public void listIncomesByCategory(String category) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded.");
            return;
        }

        System.out.println("Incomes in category: " + category);
        boolean found = false;

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

    public void printTopCategory() {
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

        String topCategory = null;
        BigDecimal maxAmount = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            if (entry.getValue().compareTo(maxAmount) > 0) {
                maxAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        System.out.printf("Top Income Category: %s ($%.2f)%n", topCategory, maxAmount);
    }


    public void printBottomCategory() {
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

        String bottomCategory = null;
        BigDecimal minAmount = null;

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            if (minAmount == null || entry.getValue().compareTo(minAmount) < 0) {
                minAmount = entry.getValue();
                bottomCategory = entry.getKey();
            }
        }

        System.out.printf("Lowest Income Category: %s ($%.2f)%n", bottomCategory, minAmount);
    }


    public int getIncomeCount() {
        return incomes.size();
    }

    public Income getIncome(int i) {
        return incomes.get(i);
    }
}
