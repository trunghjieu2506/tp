package cashflow.analytics;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private FinanceData data;

    public AnalyticsManager(FinanceData data) {
        this.data = data;
    }

    // Generate a comprehensive financial summary using integrated modules.
    public String getFinancialSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Financial Overview:\n");
        sb.append("-------------------\n");

        // Expense & Income summary.
        if (data.getExpenseIncomeManager() != null) {
            sb.append(data.getExpenseIncomeManager().getMonthlyExpenseSummary());
        } else {
            sb.append("No expense/income data available.\n");
        }

        // Savings summary.
        if (data.getSavingsManager() != null) {
            sb.append(data.getSavingsManager().getSavingsSummary());
        } else {
            sb.append("No savings data available.\n");
        }

        // Loan & Debt summary.
        if (data.getLoanDebtManager() != null) {
            sb.append(data.getLoanDebtManager().getLoanDebtSummary());
        } else {
            sb.append("No loan/debt data available.\n");
        }

        sb.append("-------------------");
        return sb.toString();
    }

    // Example suggestion method.
    public void provideSuggestions() {
        System.out.println("Suggestion: Consider reducing spending on non-essentials.");
    }

    /**
     * Example: Returns a comprehensive monthly summary report.
     * @param month The month you want the summary for (1-12).
     * @param year  The year you want the summary for (e.g., 2025).
     */
    public String getMonthlySummary(int month, int year) {
        StringBuilder sb = new StringBuilder();
        YearMonth yearMonth = YearMonth.of(year, month);

        ArrayList<Finance> monthlyFinance = retrieveTransactionsForMonth(month, year)
        // 2. Calculate total income vs total expenses for the month.
        double totalIncome = getTotalIncomeForMonth(month, year);
        double totalExpenses = getTotalExpensesForMonth(month, year);

        // Calculate net savings for the month.
        double netSavings = totalIncome - totalExpenses;

        // Find the biggest spending category (can i get the string of this rather than print?)
        data.getExpenseManager().printTopCategory();

        // Outstanding loans/debts for the month
        double totalOwed = 0.0;
        double totalReceivable = 0.0;
        for (Finance tx : monthlyFinance) {
            if (tx.getType().equalsIgnoreCase("loan")) {
                totalReceivable += tx.getAmount();
                totalOwed += tx.getAmount();
                }
            }

        // 6. Comparison with last month.
        YearMonth lastMonth = yearMonth.minusMonths(1);
        double lastMonthIncome = getTotalIncomeForMonth(lastMonth.getMonthValue(), lastMonth.getYear());
        double lastMonthExpenses = getTotalExpensesForMonth(lastMonth.getMonthValue(), lastMonth.getYear());

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

        sb.append("Biggest Spending Category: ").append(data.getExpenseManager().printTopCategory());

        sb.append("Outstanding Loans (You owe): ").append(data.getCurrency()).append(totalOwed).append("\n");
        sb.append("Outstanding Debts (Others owe you): ").append(data.getCurrency()).append(totalReceivable).append("\n");

        // Comparison with last month
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
     * Helper method to get total income for a given month/year.
     * This is used for the "comparison with last month" logic.
     */
    private double getTotalIncomeForMonth(int month, int year) {
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
    private double getTotalExpensesForMonth(int month, int year) {
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
     * Helper method to retrieve transactions for a given month/year
     * from all relevant managers.
     */
    private ArrayList<Finance> retrieveTransactionsForMonth(int month, int year) {
        ArrayList<Finance> expenseList = data.getExpenseManager() != null
                ? data.getExpenseManager().getExpenseList()
                : new ArrayList<Finance>();

        ArrayList<Finance> incomeList = data.getIncomeManager() != null
                ? data.getExpenseManager().getIncomeList();
                : new ArrayList<Finance>();
        ArrayList<Finance> savingsList = data.getSavingsManager() != null
                ? data.getSavingsManager().getSavingList()
                : new ArrayList<Finance>();

        ArrayList<Finance> loanDebtList = data.getLoanDebtManager() != null
                ? data.getLoanDebtManager().getLoanList()
                : new ArrayList<Finance>();

        // Combine them
        ArrayList<Finance> all = new ArrayList<>();
        all.addAll(expenseList);
        all.addAll(incomeList);
        all.addAll(savingsList);
        all.addAll(loanDebtList);

        // Filter by year/month
        return all.stream()
                .filter(t -> t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }

}
