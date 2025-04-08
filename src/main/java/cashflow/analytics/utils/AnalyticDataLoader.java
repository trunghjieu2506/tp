package cashflow.analytics.utils;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Acts as a data access layer specifically for analytic operations.
 * It retrieves and preprocesses data from the main {@link FinanceData} source,
 * offering convenient methods for common analytic queries like monthly totals,
 * transaction retrieval by date range, or category summaries.
 *
 * @see FinanceData
 * @see cashflow.analytics.AnalyticsManager
 */

public class AnalyticDataLoader {
    private FinanceData data;
    public AnalyticDataLoader(FinanceData data) {
        this.data = data;
    }

    /**
     * Helper method: Summarizes a list of Finance items by category.
     */
    public static Map<String, Double> sumExpensesByCategory(List<Expense> expenses) {
        Map<String, Double> totals = new HashMap<>();
        for (Expense e : expenses) {
            String cat = e.getCategory();
            totals.put(cat, totals.getOrDefault(cat, 0.0) + e.getAmount());
        }
        return totals;
    }

    /**
     * Helper method to get total income for a given month/year.
     * This is used for the "comparison with last month" logic.
     */
    public double getTotalIncomeForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("income".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to get total expenses for a given month/year.
     */
    public double getTotalExpensesForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("expense".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to calculate Net Savings for a month
     */
    public double getNetSavingsForMonth(int month, int year) {
        return getTotalIncomeForMonth(month, year) - getTotalExpensesForMonth(month, year);
    }

    /**
     * Helper method to retrieve all Expenses for a given month/year
     */
    public ArrayList<Expense> retrieveMonthlyExpenses(int month, int year) {
        ArrayList<Expense> allExpenses = data.getExpenseManager().getList();
        // Filter by year/month
        return allExpenses.stream()
                .filter(t -> t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Helper method to retrieve transactions for a given month/year
     * from all relevant managers.
     */
    public ArrayList<Finance> retrieveTransactionsForMonth(int month, int year) {
        ArrayList<Finance> allTransactions = retrieveAllTransactions();
        // Filter by year/month
        return allTransactions.stream()
                .filter(t -> t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Helper method to retrieve transactions from a given range of time
     * from all relevant managers.
     */
    public ArrayList<Finance> retrieveTransactionsFromRange(LocalDate start, LocalDate end) {
        ArrayList<Finance> allTransactions = retrieveAllTransactions();
        return allTransactions.stream()
                .filter(t -> t.getDate().isAfter(start) && t.getDate().isBefore(end))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Helper method to retrieve all transactions from all Expense and Income managers.
     */
    private ArrayList<Finance> retrieveAllTransactions(){
        ArrayList<Finance> expenseList = data.getExpenseManager() != null
                ? data.getExpenseManager().getExpenseList()
                : new ArrayList<Finance>();

        ArrayList<Finance> incomeList = data.getIncomeManager() != null
                ? data.getIncomeManager().getIncomeList()
                : new ArrayList<Finance>();

        ArrayList<Finance> allTransactions = new ArrayList<>();
        allTransactions.addAll(expenseList);
        allTransactions.addAll(incomeList);
        return allTransactions;
    }
}
