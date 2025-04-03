package cashflow.model;

import cashflow.analytics.AnalyticsManager;
import cashflow.dummy.DummyExpense;
import cashflow.dummy.DummyLoan;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.SavingManager;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;

//Centralized data hub
public class FinanceData {
    private String currency = "$";
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private BudgetManager budgetManager;
    private SavingManager savingsManager;
    private DummyLoan loanDebtManager;
    private AnalyticsManager analyticsManager;

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public IncomeManager getIncomeManager() {
        return incomeManager;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //    // Categories management.
    //    public List<String> getCategories() {
    //        return categories;
    //    }
    //    public void addCategory(String category) {
    //        this.categories.add(category);
    //    }

    public SavingManager getSavingsManager() {
        return savingsManager;
    }
    public void setSavingsManager(SavingManager savingsManager) {
        this.savingsManager = savingsManager;
    }

    public DummyLoan getLoanDebtManager() {
        return loanDebtManager;
    }
    public void setLoanDebtManager(DummyLoan loanDebtManager) {
        this.loanDebtManager = loanDebtManager;
    }

    public AnalyticsManager getAnalyticsManager() {
        return analyticsManager;
    }
    public void setAnalyticsManager(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    public void setIncomeManager(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    public BudgetManager getBudgetManager() {
        return budgetManager;
    }
}
