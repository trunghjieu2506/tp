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

    public FinanceData() {
    }

    // Integration modules for the other teams.
    // Nicholas Expense and Income Managers
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private SavingList savingsManager;
    private BudgetList budgetManager;
    private LoanManager loanManager;

    // Your analytics module.
    private AnalyticsManager analyticsManager;

    // Basic getters and setters.
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = Currency.getInstance(currency);
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

    public void setExpenseManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    public void setIncomeManager(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public IncomeManager getIncomeManager() {
        return incomeManager;
    }
}
