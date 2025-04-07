package cashflow.model;

import budgetsaving.budget.BudgetList;
import budgetsaving.saving.SavingList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.setup.SetUpManager;
import cashflow.model.storage.Storage;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import loanbook.LoanManager;

import java.io.FileNotFoundException;
import java.util.Currency;

//Centralized data hub
public class FinanceData {
    private Currency currency;
    private boolean isFirstTime;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private SavingList savingsManager;
    private BudgetList budgetManager;
    private LoanManager loanManager;
    private AnalyticsManager analyticsManager;
    private SetUpManager setUpManager;

//    public FinanceData(Storage setupStorage) throws FileNotFoundException {
//        currency = setupStorage.loadFile();
//    }

    // Basic getters and setters.
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        try{
            this.currency = Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Currency code is invalid. " +
                    "Currency should follow ISO 4271 standard: three uppercase letters");
        }
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }
    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }
    public SetUpManager getSetUpManager() {
        return setUpManager;
    }
    public void setSetUpManager(SetUpManager setUpManager) {
        this.setUpManager = setUpManager;
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

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public IncomeManager getIncomeManager() {
        return incomeManager;
    }
}
