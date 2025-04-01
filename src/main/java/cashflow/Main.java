package cashflow;

import budgetsaving.saving.SavingList;
import cashflow.dummy.DummyExpense;
import cashflow.dummy.DummyLoan;
import cashflow.model.FinanceData;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.interfaces.SavingManager;
import cashflow.ui.SetUp;
import cashflow.ui.UI;


public class Main {
    private static boolean isFirstTime = true;

    public static void main(String[] args) {

        // Initialize the central data model.
        FinanceData data = new FinanceData();

        // Initialize integration modules (dummy implementations for now).
        DummyExpense expenseIncomeManager = new DummyExpense();
        SavingManager savingManager = new SavingList();
        DummyLoan loanDebtManager = new DummyLoan();

        // Set integration points in FinanceData.
        data.setExpenseIncomeManager(expenseIncomeManager);
        data.setSavingsManager(savingManager);
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
