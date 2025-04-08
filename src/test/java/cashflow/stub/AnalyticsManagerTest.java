package cashflow.stub;

import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsManagerTest {

    private AnalyticsManager analyticsManager;
    private FinanceData mockData;
    private ExpenseManager mockExpenseManager;
    private IncomeManager mockIncomeManager;

    @BeforeEach
    void setUp() {
        // Create mock or stub FinanceData
        mockData = Mockito.mock(FinanceData.class);

        // Create stub managers for expense and income
        mockExpenseManager = Mockito.mock(ExpenseManager.class);
        mockIncomeManager = Mockito.mock(IncomeManager.class);

        // When FinanceData is asked for expenseManager or incomeManager, return stubs
        Mockito.when(mockData.getExpenseManager()).thenReturn(mockExpenseManager);
        Mockito.when(mockData.getIncomeManager()).thenReturn(mockIncomeManager);

        Mockito.when(mockData.getCurrency()).thenReturn(Currency.getInstance("USD"));

        // Create a new AnalyticsManager with the mocked data
        analyticsManager = new AnalyticsManager(mockData);
    }

    @Test
    void testGetMonthlySummary_noData() {
        // Suppose no expenses or incomes for this month
        Mockito.when(mockExpenseManager.getExpenseList()).thenReturn(new ArrayList<>());
        Mockito.when(mockIncomeManager.getIncomeList()).thenReturn(new ArrayList<>());

        // Check the result string
        String summary = analyticsManager.getMonthlySummary(4, 2025);

        assertTrue(summary.contains("Monthly Summary for APRIL 2025"));
        assertTrue(summary.contains("Total Income: USD 0.0"));
        assertTrue(summary.contains("Total Expenses: USD 0.0"));
        assertTrue(summary.contains("Net Savings (Income - Expense): USD 0.0"));
    }

    @Test
    void testGetMonthlySummary_withData() {
        // Suppose we have some expenses in April 2025
        List<Finance> expenseList = new ArrayList<>();
        expenseList.add(createExpense(100.0, 2025, 4, 10));
        expenseList.add(createExpense(200.0, 2025, 4, 15));

        // Suppose we have some incomes in April 2025
        List<Finance> incomeList = new ArrayList<>();
        incomeList.add(createIncome(300.0, 2025, 4, 5));

        // Return these from the managers
        Mockito.when(mockExpenseManager.getExpenseList()).thenReturn(new ArrayList<>(expenseList));
        Mockito.when(mockIncomeManager.getIncomeList()).thenReturn(new ArrayList<>(incomeList));

        // Now call the method under test
        String summary = analyticsManager.getMonthlySummary(4, 2025);

        // Verify the string includes the expected amounts
        assertTrue(summary.contains("Monthly Summary for APRIL 2025"));
        assertTrue(summary.contains("Total Income: USD 300.0"));
        assertTrue(summary.contains("Total Expenses: USD 300.0")); // sum of 100 + 200
        assertTrue(summary.contains("Net Savings (Income - Expense): USD 0.0"));
    }

    @Test
    void testShowSpendingInsights_noData() {
        // Stub empty expenses
        Mockito.when(mockExpenseManager.getExpenseList()).thenReturn(new ArrayList<>());
        Mockito.when(mockIncomeManager.getIncomeList()).thenReturn(new ArrayList<>());

        // Just ensure no crash
        assertDoesNotThrow(() -> analyticsManager.showSpendingInsights(4, 2025));
    }

    /**
     * Helper method to create a stub expense with given amount, year, month, day
     */
    private Finance createExpense(double amount, int year, int month, int day) {
        Finance mockExpense = Mockito.mock(Finance.class);
        Mockito.when(mockExpense.getType()).thenReturn("expense");
        Mockito.when(mockExpense.getDate()).thenReturn(LocalDate.of(year, month, day));
        Mockito.when(mockExpense.getAmount()).thenReturn(amount);
        return mockExpense;
    }

    /**
     * Helper method to create a stub income with given amount, year, month, day
     */
    private Finance createIncome(double amount, int year, int month, int day) {
        Finance mockIncome = Mockito.mock(Finance.class);
        Mockito.when(mockIncome.getType()).thenReturn("income");
        Mockito.when(mockIncome.getDate()).thenReturn(LocalDate.of(year, month, day));
        Mockito.when(mockIncome.getAmount()).thenReturn(amount);
        return mockIncome;
    }
}
