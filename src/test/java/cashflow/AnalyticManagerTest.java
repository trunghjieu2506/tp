package cashflow;

import budgetsaving.budget.BudgetList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.model.interfaces.BudgetManager;
import cashflow.stub.StubExpenseManager;
import cashflow.stub.StubIncomeManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Example JUnit 5 test class for AnalyticsManager.
 */
class AnalyticsManagerTest {

    private FinanceData financeData;
    private AnalyticsManager analyticsManager;

    @BeforeEach
    void setUp() {
        // 1. Create a new FinanceData and AnalyticsManager
        financeData = new FinanceData();
        analyticsManager = new AnalyticsManager(financeData);
        financeData.setAnalyticsManager(analyticsManager);

        // 3. Set a currency for testing
        financeData.setCurrency("USD");

        // 2. Create and set stub managers (expense, income, loan, etc.) that return test data
        BudgetManager budgetManager = new BudgetList(Currency.getInstance("USD"));
        financeData.setExpenseManager(new StubExpenseManager(budgetManager));
        financeData.setIncomeManager(new StubIncomeManager());
//        financeData.setLoanManager(new StubLoanManager());

    }

    @Test
    void testGetMonthlySummary_noData() {
        // If stubs have no matching data for this month, the summary should reflect zero amounts.
        String summary = analyticsManager.getMonthlySummary(12, 2050);
        // Just do some minimal checks or verify parts of the string.
        assertTrue(summary.contains("Monthly Summary for DECEMBER 2050"));
        assertTrue(summary.contains("Total Income: USD0.0"));
//        assertTrue(summary.contains("Biggest Spending Category: Food (USD100.0)"));
        assertTrue(summary.contains("Total Expenses: USD0.0"));
    }

    @Test
    void testGetMonthlySummary_withData() {
        // Our stubs will supply data for 2025-04, so let's see if it's summarized correctly
        String summary = analyticsManager.getMonthlySummary(4, 2025);

        assertTrue(summary.contains("Monthly Summary for APRIL 2025"));
        assertTrue(summary.contains("Total Income: USD300.0"));
        assertTrue(summary.contains("Total Expenses: USD100.0"));
//        assertTrue(summary.contains("Biggest Spending Category: Food (USD100.0)"));

        assertTrue(summary.contains("Net Savings (Income - Expense): USD200.0"));
    }
}
