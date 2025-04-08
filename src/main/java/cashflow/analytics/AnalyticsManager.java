package cashflow.analytics;

import cashflow.analytics.utils.AnalyticDataLoader;
import cashflow.analytics.utils.SpendingHelper;
import cashflow.analytics.utils.TrendHelper;
import cashflow.analytics.utils.InsightHelper;
import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;

import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.logging.*;

public class AnalyticsManager {
    /** Logger for analytics operations. */
    private static final Logger logger = Logger.getLogger(AnalyticsManager.class.getName());

    static {
        try {
            // Ensure "logs" directory exists
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // (Optional) remove default console handlers if you only want file logs
            Logger rootLogger = Logger.getLogger("");
            for (Handler h : rootLogger.getHandlers()) {
                rootLogger.removeHandler(h);
            }

            FileHandler fileHandler = new FileHandler("logs/analytics.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);

            rootLogger.setLevel(Level.ALL);
            logger.setLevel(Level.ALL);

        } catch (Exception e) {
            System.err.println("Failed to initialize AnalyticsManager logger: " + e.getMessage());
        }
    }

    private FinanceData data;
    private AnalyticDataLoader dataLoader;

    public AnalyticsManager(FinanceData data) {
        assert data != null : "FinanceData cannot be null for AnalyticsManager";

        this.data = data;
        this.dataLoader = new AnalyticDataLoader(data);
        assert this.dataLoader != null : "AnalyticDataLoader failed to initialize";
        logger.info("AnalyticsManager created with parameter FinanceData.");
    }
    /**
     * Example: Returns a comprehensive monthly summary report.
     * @param month The month you want the summary for (1-12).
     * @param year  The year you want the summary for (e.g., 2025).
     */
    public String getMonthlySummary(int month, int year) {
        logger.info(String.format("Generating monthly summary for %d/%d", month, year));

        StringBuilder sb = new StringBuilder();
        YearMonth yearMonth = YearMonth.of(year, month);

        assert yearMonth != null : "YearMonth creation failed unexpectedly";


        double totalIncome = dataLoader.getTotalIncomeForMonth(month, year);
        double totalExpenses = dataLoader.getTotalExpensesForMonth(month, year);
        double netSavings = dataLoader.getNetSavingsForMonth(month, year);

        logger.fine(String.format("Income=%.2f, Expenses=%.2f, NetSavings=%.2f",
                totalIncome, totalExpenses, netSavings));

        // Comparison with last month.
        YearMonth lastMonth = yearMonth.minusMonths(1);
        double lastMonthIncome = dataLoader.getTotalIncomeForMonth(lastMonth.getMonthValue(), lastMonth.getYear());
        double lastMonthExpenses = dataLoader.getTotalExpensesForMonth(lastMonth.getMonthValue(), lastMonth.getYear());

        // Build the summary string.

        sb.append("---------------------------------\n");
        sb.append("\nMonthly Summary for ")
                .append(yearMonth.getMonth().toString())
                .append(" ")
                .append(year)
                .append(":");
        sb.append("\n- Total Income: ").append(data.getCurrency()).append(" ").append(totalIncome).append("\n");
        sb.append("- Total Expenses: ").append(data.getCurrency()).append(" ").append(totalExpenses).append("\n");
        sb.append("- Net Savings (Income - Expense): ")
                .append(data.getCurrency()).append(" ").append(netSavings).append("\n");

        // Comparison with last month
        sb.append("\n---------------------------------\n");
        sb.append("\nComparison with Last Month (")
                .append(lastMonth.getMonth())
                .append(" ")
                .append(lastMonth.getYear())
                .append("):\n");
        sb.append(" - Income: ").append(data.getCurrency()).append(" ").append(totalIncome)
                .append(" vs ").append(data.getCurrency()).append(lastMonthIncome).append("\n");
        sb.append(" - Expenses: ").append(data.getCurrency()).append(" ").append(totalExpenses)
                .append(" vs ").append(data.getCurrency()).append(lastMonthExpenses).append("\n");

        sb.append("\n---------------------------------\n");

        logger.info("Monthly summary generated successfully.");

        return sb.toString();
    }


    /**
     * 2. Trend Over Time:
     * Example method to print a simple textual line chart for
     * weekly or monthly sums of either expenses, income, or savings.
     *
     * @param dataType "expense" or "income"
     * @param start    Start date for the chart
     * @param end      End date for the chart
     * @param interval "weekly" or "monthly"
     */
    public void showTrendOverTime(String dataType, LocalDate start, LocalDate end, String interval) {
        logger.info(String.format("showTrendOverTime called: dataType=%s, start=%s, end=%s, interval=%s",
                dataType, start, end, interval));

        // Gather all transactions in [start, end], filter by dataType
        List<Finance> allTransactions = dataLoader.retrieveTransactionsFromRange(start, end).stream()
                .filter(t -> t.getType().equalsIgnoreCase(dataType))
                .toList();

        if ("weekly".equalsIgnoreCase(interval)) {
            List<Map.Entry<String, Double>> sortedWeekly
                    = TrendHelper.processWeeklyTrend(allTransactions, start, end);
            // Print the chart
            TrendHelper.printTrendChart(sortedWeekly, dataType, start, end, "weekly");
        }
        else {
            List<Map.Entry<String, Double>> sortedMonthly
                    = TrendHelper.processMonthlyTrend(allTransactions, start, end);
            // Print the chart
            TrendHelper.printTrendChart(sortedMonthly, dataType, start, end, "monthly");
        }
        logger.info("TrendOverTime chart displayed successfully.");
    }

    /**
     * Provides spending insights (new spending category) and recommendations by comparing the given month
     * with the previous month. Example: "You spent 30% more on dining this month than last month."
     *
     * @param month the month you want spending insights
     * @param year  the year you want spending insights
     */
    public void showSpendingInsights(int month, int year) {
        YearMonth current = YearMonth.of(year, month);
        YearMonth last = current.minusMonths(1);

        logger.info(String.format("showSpendingInsights for %s vs %s", current, last));

        // Retrieve expense data for both months
        ArrayList<Expense> thisMonthExpenses = dataLoader.retrieveMonthlyExpenses( month, year);
        ArrayList<Expense> lastMonthExpenses = dataLoader.retrieveMonthlyExpenses(
                last.getMonthValue(), last.getYear());

        if (thisMonthExpenses.isEmpty() && lastMonthExpenses.isEmpty()) {
            System.out.println("No spending data for " + current + " or " + last + ".");
            logger.fine("No expense data found for either month.");
            return;
        }

        // Sum expenses by category for each month
        Map<String, Double> currentTotals = AnalyticDataLoader.sumExpensesByCategory(thisMonthExpenses);
        Map<String, Double> lastTotals = AnalyticDataLoader.sumExpensesByCategory(lastMonthExpenses);

        //Helper function that takes care of insight logic and display insights
        InsightHelper.displayInsights(current, last, currentTotals, lastTotals);
        logger.info("Finished spending insights comparison.");
    }

    /**
     * Displays a spending breakdown of expenses in terms of categories for a given month/year.
     * Shows a bar diagram and percentage share per category.
     *
     * @param month the month you want spending insights
     * @param year  the year you want spending insights
     */
    public void showSpendingBreakDown(int month, int year) {
        ArrayList<Expense> monthlyExpenses = dataLoader.retrieveMonthlyExpenses(month, year);

        if (monthlyExpenses.isEmpty()) {
            System.out.println("No expenses recorded for " + YearMonth.of(year, month) + ".");
            logger.fine("No expenses found for that month.");
            return;
        }

        double grandTotal = dataLoader.getTotalExpensesForMonth(month, year);
        Map<String, Double> categoryTotals = AnalyticDataLoader.sumExpensesByCategory(monthlyExpenses);
        // Helper function to print chart
        SpendingHelper.printChart(month, year, grandTotal, categoryTotals);
        logger.info("Category breakdown displayed.");
    }
}
