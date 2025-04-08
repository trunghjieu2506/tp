package cashflow.model;

import budgetsaving.budget.BudgetList;
import budgetsaving.saving.SavingList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.setup.SetUpManager;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import loanbook.LoanManager;
import java.util.Currency;

/**
 * Acts as a central container aggregating references to all major data management
 * components and core configuration settings for the personal finance application.
 */
public class FinanceData {

    /**
     * The primary currency used for all financial amounts within the application.
     * Should conform to ISO 4271 (e.g., USD, EUR, SGD).
     */
    private Currency currency;

    /**
     * Flag indicating whether the application is being run for the first time by the user.
     * Typically used to trigger initial setup procedures.
     */
    private boolean isFirstTime;

    /**
     * Manager responsible for handling expense records and operations.
     */
    private ExpenseManager expenseManager;

    /**
     * Manager responsible for handling income records and operations.
     */
    private IncomeManager incomeManager;

    /**
     * List/Manager responsible for handling savings goals and records.
     * Note: Type is {@link SavingList}.
     */
    private SavingList savingsManager;

    /**
     * List/Manager responsible for handling budget definitions and tracking.
     * Note: Type is {@link BudgetList}.
     */
    private BudgetList budgetManager;

    /**
     * Manager responsible for handling loan records and operations.
     */
    private LoanManager loanManager;

    /**
     * Manager responsible for providing financial analysis and insights.
     */
    private AnalyticsManager analyticsManager;

    /**
     * Manager responsible for handling the initial application setup process.
     */
    private SetUpManager setUpManager;

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
