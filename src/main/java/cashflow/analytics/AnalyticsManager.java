package cashflow.analytics;

import cashflow.model.FinanceData;

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
}
