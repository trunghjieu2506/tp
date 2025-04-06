package cashflow;

import budgetsaving.budget.BudgetList;
import budgetsaving.saving.SavingList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.model.storage.Storage;
import cashflow.ui.UI;
import cashflow.ui.command.SetUpCommand;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import loanbook.LoanManager;

import java.util.Currency;

public class CashFlowManager {

    /** Storage component responsible for reading and writing task data. */
    private final Storage storageExpense;
    private final Storage storageIncome;
    private final Storage storageSaving;
    private final Storage storageLoan;
    private final Storage storageContact;

    private SavingList savingManager;
    private BudgetList budgetManager;
    private LoanManager loanManager;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;

    private FinanceData data;

    //Initialization
    public CashFlowManager(){

        String expenseFile = "src/main/java/cashflow/model/storage/expense.dat";
        String incomeFile = "src/main/java/cashflow/model/storage/income.dat";
        String savingFile = "src/main/java/cashflow/model/storage/saving.dat";
        String loanFile = "src/main/java/cashflow/model/storage/loan.dat";
        String contactFile = "src/main/java/cashflow/model/storage/contact.dat";

        // take these objects as arguments in your Manager constructor
        storageExpense = new Storage(expenseFile);
        storageIncome = new Storage(incomeFile);
        storageSaving = new Storage(savingFile);
        storageLoan = new Storage(loanFile);
        storageContact = new Storage(contactFile);

        data = new FinanceData();
        String currencyStr = "USD"; //data.getCurrency().getCurrencyCode();
        Currency currency = Currency.getInstance(currencyStr);

        // Initialize integration modules (dummy implementations for now).

        expenseManager = new ExpenseManager(data, currencyStr);     //need to change this part to accept Currency class
        incomeManager = new IncomeManager(data, currencyStr);
        savingManager = new SavingList(currencyStr);
        budgetManager = new BudgetList(currency);
        this.loanManager = new LoanManager("CashFlow");


        // Set integration points in FinanceData.
        data.setExpenseManager(expenseManager);
        data.setIncomeManager(incomeManager);
        data.setSavingsManager(savingManager);
        data.setBudgetManager(budgetManager);
        data.setLoanManager(loanManager);

        // Initialize Analytics module.
        AnalyticsManager analyticsManager = new AnalyticsManager(data);
        data.setAnalyticsManager(analyticsManager);
    }


    private static boolean isFirstTime = true;
    /**
     * Runs the main application loop.
     * Continuously reads user commands, parses them, and executes the corresponding actions
     * until the ExitCommand is issued.
     */
    public void run() {
        UI ui = new UI(data);

        // Display a welcome message with an initial financial summary.
        System.out.println("welcome to cashflow!");
//        System.out.println(data.getAnalyticsManager().getFinancialSummary());
        System.out.println();

        // Run first-time setup.
        if (isFirstTime) {
            new SetUpCommand(data).execute();
            isFirstTime = false;
        }
        // Start the command-line UI loop.
        ui.run();
    }
    /**
     * Default path for storing the task list file.
     * Creates a hidden directory named '.corgimanager' in the user's home folder
     * and stores tasks in 'tasks.dat'.
     */
}
