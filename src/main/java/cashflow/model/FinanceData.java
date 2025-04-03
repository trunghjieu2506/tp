package cashflow.model;

import budgetsaving.budget.BudgetList;
import budgetsaving.saving.SavingList;
import cashflow.analytics.AnalyticsManager;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import loanbook.LoanManager;

import java.util.Currency;

//Centralized data hub
public class FinanceData {
    private Currency currency;
    private AnalyticsManager analyticsManager;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private SavingList savingsManager;
    private BudgetList budgetManager;
    private LoanManager loanManager;

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = Currency.getInstance(currency);
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }
    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    public IncomeManager getIncomeManager() {
        return incomeManager;
    }
    public void setIncomeManager(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    public SavingList getSavingsManager() {
        return savingsManager;
    }
    public void setSavingsManager(SavingList savingsManager) {
        this.savingsManager = savingsManager;
    }

    public BudgetList getBudgetManager() {
        return budgetManager;
    }
    public void setBudgetManager(BudgetList budgetManager) {
        this.budgetManager = budgetManager;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }
    public void setLoanManager(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    public AnalyticsManager getAnalyticsManager() {
        return analyticsManager;
    }
    public void setAnalyticsManager(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }


}
