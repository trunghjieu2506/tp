package cashflow;

import cashflow.analytics.AnalyticsManager;
import cashflow.dummy.DummyLoan;
//import cashflow.dummy.DummySavings;
import cashflow.model.FinanceData;
import cashflow.model.storage.Storage;
import cashflow.ui.SetUp;
import cashflow.ui.UI;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;

public class CashFlowManager {

    /** Storage component responsible for reading and writing task data. */
    private final Storage storageExpense;
    private final Storage storageIncome;
    private final Storage storageSaving;
    private final Storage storageLoan;

    private FinanceData data;

    //Initialization
    public CashFlowManager(){

        String expenseFile = "src/main/java/cashflow/model/storage/expense.dat";
        String incomeFile = "src/main/java/cashflow/model/storage/income.dat";
        String savingFile = "src/main/java/cashflow/model/storage/saving.dat";
        String loanFile = "src/main/java/cashflow/model/storage/loan.dat";

        // take these objects as arguments in your Manager constructor
        storageExpense = new Storage(expenseFile);
        storageIncome = new Storage(incomeFile);
        storageSaving = new Storage(savingFile);
        storageLoan = new Storage(loanFile);

        data = new FinanceData();

        // Initialize integration modules (dummy implementations for now).
        ExpenseManager expenseManager = new ExpenseManager();
        IncomeManager incomeManager = new IncomeManager();
        DummySavings savingsManager = new DummySavings();
        DummyLoan loanDebtManager = new cashflow.dummy.DummyLoan();

        // Set integration points in FinanceData.
        data.setExpenseManager(expenseManager);
        data.setIncomeManager(incomeManager);
        data.setSavingsManager(savingsManager);
        data.setLoanDebtManager(loanDebtManager);

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
        System.out.println(data.getAnalyticsManager().getFinancialSummary());
        System.out.println();

        // Run first-time setup.
        if (isFirstTime) {
            SetUp setup = new SetUp(data);
            setup.run();
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
