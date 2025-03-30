//package cashflow;
//
//import cashflow.analytics.AnalyticsManager;
//import cashflow.dummy.DummyExpense;
//import cashflow.dummy.DummyLoan;
//import cashflow.dummy.DummySavings;
//import cashflow.model.FinanceData;
//import cashflow.model.storage.Storage;
//import cashflow.ui.SetUp;
//import cashflow.ui.UI;
//import saving.SavingList;
//
//import java.util.ArrayList;
//
//public class CashFlowManager {
//
//    /** Storage component responsible for reading and writing task data. */
//    private final Storage storage;
//    private FinanceData data;
//    private SavingList savingList;
//
//    /**
//     * Constructs a CorgiManager instance with the specified file path for storage.
//     * Loads TaskList from storage or creates a new empty list upon failure.
//     *
//     * @param filePath Path to the storage file.
//     */
//
//    //Initialization
//    public CashFlowManager(){
//
//        String filePath = "savings.dat"
//        storage = new Storage(filePath);
//        try {
//            savingList = new SavingList(storage.loadSavings());
//        } catch (Exception e) {
//        }
//
//        data = new FinanceData();
//
//        // Initialize integration modules (dummy implementations for now).
//        DummyExpense expenseIncomeManager = new DummyExpense();
//        DummySavings savingsManager = new DummySavings();
//        DummyLoan loanDebtManager = new cashflow.dummy.DummyLoan();
//
//        // Set integration points in FinanceData.
//        data.setExpenseIncomeManager(expenseIncomeManager);
//        data.setSavingsManager(savingsManager);
//        data.setLoanDebtManager(loanDebtManager);
//
//        // Initialize Analytics module.
//        AnalyticsManager analyticsManager = new AnalyticsManager(data);
//        data.setAnalyticsManager(analyticsManager);
//    }
//
//
//    private static boolean isFirstTime = true;
//    /**
//     * Runs the main application loop.
//     * Continuously reads user commands, parses them, and executes the corresponding actions
//     * until the ExitCommand is issued.
//     */
//    public void run() {
//        UI ui = new UI(data);
//        ui.welcome();
//        // Run first-time setup.
//        if (isFirstTime) {
//            SetUp setup = new SetUp(data);
//            setup.run();
//            isFirstTime = false;
//        }
//        // Start the command-line UI loop.
//        ui.run();
//    }
//}
//
///**
// * Default path for storing the task list file.
// * Creates a hidden directory named '.corgimanager' in the user's home folder
// * and stores tasks in 'tasks.dat'.
// */
//
//public static void main(String[] args) {
//    new CashFlowManager().run();
//}
//
//}
