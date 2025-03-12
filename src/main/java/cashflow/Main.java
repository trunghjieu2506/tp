package cashflow;

import cashflow.dummy.DummyExpense;
import cashflow.dummy.DummyLoan;
import cashflow.dummy.DummySavings;
import cashflow.model.FinanceData;
import cashflow.analytics.AnalyticsManager;
import cashflow.ui.*;


public class Main {
    private static boolean isFirstTime = true;

    public static void main(String[] args) {
        // Initialize the central data model.
        FinanceData data = new FinanceData();

        // Initialize integration modules (dummy implementations for now).
        DummyExpense expenseIncomeManager = new DummyExpense();
        DummySavings savingsManager = new DummySavings();
        DummyLoan loanDebtManager = new cashflow.dummy.DummyLoan();

        // Set integration points in FinanceData.
        data.setExpenseIncomeManager(expenseIncomeManager);
        data.setSavingsManager(savingsManager);
        data.setLoanDebtManager(loanDebtManager);

        // Initialize Analytics module.
        AnalyticsManager analyticsManager = new AnalyticsManager(data);
        data.setAnalyticsManager(analyticsManager);

        // Display a welcome message with an initial financial summary.
        System.out.println("welcome to cashflow!");
        System.out.println(analyticsManager.getFinancialSummary());
        System.out.println();

        // Run first-time setup.
        if (isFirstTime) {
            SetUp setup = new SetUp(data);
            setup.run();
            isFirstTime = false;
        }

        // Start the command-line UI loop.
        UI ui = new UI(data);
        ui.run();
    }
}
