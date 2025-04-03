package cashflow.analytics;

import cashflow.analytics.utils.AnalyticDataLoader;
import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private FinanceData data;
    private AnalyticDataLoader dataLoader;

    public AnalyticsManager(FinanceData data) {
        this.data = data;
        this.dataLoader = new AnalyticDataLoader(data);
    }
    /**
     * Example: Returns a comprehensive monthly summary report.
     * @param month The month you want the summary for (1-12).
     * @param year  The year you want the summary for (e.g., 2025).
     */
    public String getMonthlySummary(int month, int year) {
        StringBuilder sb = new StringBuilder();
        YearMonth yearMonth = YearMonth.of(year, month);


        double totalIncome = dataLoader.getTotalIncomeForMonth(month, year);
        double totalExpenses = dataLoader.getTotalExpensesForMonth(month, year);
        double netSavings = dataLoader.getNetSavingsForMonth(month, year);
        double totalOwed = dataLoader.getTotalDebtForMonth(month, year);
        double totalReceivable = dataLoader.getTotalLoanForMonth(month, year);

        // 6. Comparison with last month.
        YearMonth lastMonth = yearMonth.minusMonths(1);
        double lastMonthIncome = dataLoader.getTotalIncomeForMonth(lastMonth.getMonthValue(), lastMonth.getYear());
        double lastMonthExpenses = dataLoader.getTotalExpensesForMonth(lastMonth.getMonthValue(), lastMonth.getYear());

        // Build the summary string.
        sb.append("Monthly Summary for ")
                .append(yearMonth.getMonth().toString())
                .append(" ")
                .append(year)
                .append("\n");
        sb.append("---------------------------------\n");
        sb.append("Total Income: ").append(data.getCurrency()).append(totalIncome).append("\n");
        sb.append("Total Expenses: ").append(data.getCurrency()).append(totalExpenses).append("\n");
        sb.append("Net Savings (Income - Expense): ")
                .append(data.getCurrency()).append(netSavings).append("\n");

        sb.append("Biggest Spending Category: ").append(data.getExpenseManager().getTopCategory()).append("\n");

        sb.append("Outstanding Loans (You owe): ").append(data.getCurrency()).append(totalOwed).append("\n");
        sb.append("Outstanding Debts (Others owe you): ").append(data.getCurrency()).append(totalReceivable).append("\n");
        // Comparison with last month
        sb.append("---------------------------------\n");
        sb.append("\nComparison with Last Month (")
                .append(lastMonth.getMonth())
                .append(" ")
                .append(lastMonth.getYear())
                .append("):\n");
        sb.append(" - Income: ").append(data.getCurrency()).append(totalIncome)
                .append(" vs ").append(data.getCurrency()).append(lastMonthIncome).append("\n");
        sb.append(" - Expenses: ").append(data.getCurrency()).append(totalExpenses)
                .append(" vs ").append(data.getCurrency()).append(lastMonthExpenses).append("\n");

        sb.append("---------------------------------\n");
        return sb.toString();
    }


    /**
     * 2. Trend Over Time:
     * Example method to print a simple textual line chart for
     * weekly or monthly sums of either expenses, income, or savings.
     *
     * @param dataType "expense", "income", or "savings"
     * @param start    Start date for the chart
     * @param end      End date for the chart
     * @param interval "weekly" or "monthly"
     */
    public void showTrendOverTime(String dataType, LocalDate start, LocalDate end, String interval) {

        ArrayList<Finance> allTransactions = dataLoader.retrieveTransactionsFromRange(start, end);
        // Filter by dataType (expense, income, savings, loan, debt)
        allTransactions = allTransactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase(dataType))
                .collect(Collectors.toCollection(ArrayList::new));

        // Group by interval (weekly or monthly)
        Map<String, Double> groupedData = new LinkedHashMap<>();

        if ("weekly".equalsIgnoreCase(interval)) {
            for (Finance t : allTransactions) {
                // Find the "week of year" or any custom logic
                // Alternatively, we can store a string "YYYY-WW" as the group key.
                String groupKey = getWeekGroupKey(t.getDate());
                groupedData.put(groupKey, groupedData.getOrDefault(groupKey, 0.0) + t.getAmount());
            }
        } else {
            // monthly grouping
            for (Finance t : allTransactions) {
                YearMonth ym = YearMonth.of(t.getDate().getYear(), t.getDate().getMonthValue());
                String groupKey = ym.toString();
                groupedData.put(groupKey, groupedData.getOrDefault(groupKey, 0.0) + t.getAmount());
            }
        }

        // 5. Print out a simple textual line chart (ASCII style)
        System.out.println("Trend Over Time for " + dataType.toUpperCase() + " (" + interval + "):");
        System.out.println("From " + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                " to " + end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("--------------------------------------------");

        // Find the max value to scale the chart
        double maxValue = groupedData.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        for (Map.Entry<String, Double> entry : groupedData.entrySet()) {
            String label = entry.getKey();
            double amount = entry.getValue();
            int barLength = maxValue > 0 ? (int)((amount / maxValue) * 50) : 0; // scale to 50 chars
            String bar = new String(new char[barLength]).replace('\0', '#');
            System.out.printf("%-10s | %s (%.2f)\n", label, bar, amount);
        }

        System.out.println("--------------------------------------------");
    }

    private String getWeekGroupKey(LocalDate date) {
        int year = date.getYear();
        // For the week number, you can use ISO fields if needed:
        int weekOfYear = date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return year + "-W" + weekOfYear;
    }

    /**
     * Displays a category breakdown of expenses for a given month/year.
     * Shows a bar diagram and percentage share per category.
     */
    public void showCategoryBreakdown(int month, int year) {
        ArrayList<Expense> monthlyExpenses = data.getExpenseManager().getList();

        if (monthlyExpenses.isEmpty()) {
            System.out.println("No expenses recorded for " + YearMonth.of(year, month) + ".");
            return;
        }

        // 2. Sum expenses by category
        Map<String, Double> categoryTotals = new HashMap<>();
        double grandTotal = 0.0;
        for (Expense ex : monthlyExpenses) {
            String category = ex.getCategory();
            double amount = ex.getAmount();
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
            grandTotal += amount;
        }

        // 3. Print results with an ASCII bar chart
        System.out.println("Category Breakdown for " + YearMonth.of(year, month) + ":");
        System.out.println("--------------------------------------------------");

        double maxValue = categoryTotals.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double total = entry.getValue();
            // Compute percentage share
            double percent = (total / grandTotal) * 100.0;

            // Build the bar scaled to e.g. 50 characters wide
            int barLength = maxValue > 0 ? (int)((total / maxValue) * 50) : 0;
            String bar = new String(new char[barLength]).replace('\0', '#');

            // Print line: Category | ### (amount) XX%
            System.out.printf("%-15s | %-50s (%.2f) [%.1f%%]\n", category, bar, total, percent);
        }

        System.out.printf("Grand Total: %.2f\n", grandTotal);
        System.out.println("--------------------------------------------------");
    }

    /**
     * Provides spending insights and recommendations by comparing the given month
     * with the previous month. Example: "You spent 30% more on dining this month than last month."
     */
    public void showSpendingInsights(int month, int year) {
        // 1. Identify current and last month
        YearMonth current = YearMonth.of(year, month);
        YearMonth last = current.minusMonths(1);

        // 2. Retrieve expense data for both months
        ArrayList<Expense> thisMonthExpenses = dataLoader.retrieveMonthlyExpenses( month, year);
        ArrayList<Expense> lastMonthExpenses = dataLoader.retrieveMonthlyExpenses(
                last.getMonthValue(), last.getYear());

        if (thisMonthExpenses.isEmpty() && lastMonthExpenses.isEmpty()) {
            System.out.println("No spending data for current or last month.");
            return;
        }

        // 3. Sum expenses by category for each month
        Map<String, Double> currentTotals = sumExpensesByCategory(thisMonthExpenses);
        Map<String, Double> lastTotals = sumExpensesByCategory(lastMonthExpenses);

        // 4. Compare for categories that exist in either month
        Set<String> allCategories = new HashSet<>();
        allCategories.addAll(currentTotals.keySet());
        allCategories.addAll(lastTotals.keySet());

        System.out.println("Spending Insights for " + current + " vs. " + last + ":");
        System.out.println("--------------------------------------------------");

        boolean foundSignificantChange = false;

        for (String category : allCategories) {
            double currentAmount = currentTotals.getOrDefault(category, 0.0);
            double lastAmount = lastTotals.getOrDefault(category, 0.0);

            // If lastAmount > 0, compute percentage difference
            if (lastAmount > 0) {
                double diff = currentAmount - lastAmount;
                double percentChange = (diff / lastAmount) * 100.0;

                // Only show if there's a notable change, e.g. +/- 20%
                if (Math.abs(percentChange) >= 20.0) {
                    foundSignificantChange = true;
                    if (percentChange > 0) {
                        System.out.printf("You spent %.1f%% more on %s this month than last month. (%.2f vs. %.2f)\n",
                                percentChange, category, currentAmount, lastAmount);
                    } else {
                        System.out.printf("You spent %.1f%% less on %s this month than last month. (%.2f vs. %.2f)\n",
                                Math.abs(percentChange), category, currentAmount, lastAmount);
                    }
                }
            } else if (currentAmount > 0 && lastAmount == 0) {
                // No spending last month, but we have some this month
                System.out.printf("New spending in category '%s': %.2f (no spending last month)\n",
                        category, currentAmount);
            }
        }

        // 5. Provide general recommendations
        System.out.println("--------------------------------------------------");
        if (!foundSignificantChange) {
            System.out.println("No significant changes detected in spending categories.");
        }
    }

    /**
     * Helper method: Summarizes a list of Finance items by category.
     */
    private Map<String, Double> sumExpensesByCategory(List<Expense> expenses) {
        Map<String, Double> totals = new HashMap<>();
        for (Expense e : expenses) {
            String cat = e.getCategory();
            totals.put(cat, totals.getOrDefault(cat, 0.0) + e.getAmount());
        }
        return totals;
    }

}
