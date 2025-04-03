package cashflow;

import budgetsaving.budget.BudgetList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.model.interfaces.BudgetManager;
import cashflow.stub.StubExpenseManager;
import cashflow.stub.StubIncomeManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        financeData.setCurrency("$");

        // 2. Create and set stub managers (expense, income, loan, etc.) that return test data
        BudgetManager budgetManager = new BudgetList("$");
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
        assertTrue(summary.contains("Total Income: $0.0"));
        assertTrue(summary.contains("Total Expenses: $0.0"));
    }

    @Test
    void testGetMonthlySummary_withData() {
        // Our stubs will supply data for 2025-04, so let's see if it's summarized correctly
        String summary = analyticsManager.getMonthlySummary(4, 2025);

        // Check for expected lines in the summary
        assertTrue(summary.contains("Monthly Summary for APRIL 2025"));
        // Suppose our stub data had $300 income
        assertTrue(summary.contains("Total Income: $300.0"));
        // Suppose our stub data had $100 expenses
        assertTrue(summary.contains("Total Expenses: $100.0"));
        // Net savings should be $200
        assertTrue(summary.contains("Net Savings (Income - Expense): $200.0"));

        // Additional checks for biggest category, or loan amounts, etc.
        // (Depending on how your stubs are implemented)
    }
}
