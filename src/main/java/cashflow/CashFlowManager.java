package cashflow;

import budgetsaving.budget.BudgetList;
import budgetsaving.saving.SavingList;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.model.setup.SetUpManager;
import cashflow.model.setup.SetupConfig;
import cashflow.model.storage.Storage;
import cashflow.ui.UI;
import cashflow.model.setup.SetUpCommand;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;
import loanbook.LoanManager;

import java.io.FileNotFoundException;
import java.util.Currency;

public class CashFlowManager {

    /** Storage component responsible for reading and writing task data. */
    private final Storage expenseStorage;
    private final Storage incomeStorage;
    private final Storage savingStorage;
    private final Storage loanStorage;
    private final Storage contactStorage;
    private final Storage setupStorage;

    private SavingList savingManager;
    private BudgetList budgetManager;
    private LoanManager loanManager;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;
    private SetUpManager setUpManager;

    private FinanceData data;

    private static boolean isFirstTime;
    private boolean isExit = false;

    //Initialization
    public CashFlowManager() throws FileNotFoundException {

        String expenseFile = "data/expense.dat";
        String incomeFile = "data/income.dat";
        String savingFile = "data/saving.dat";
        String loanFile = "data/loan.dat";
        String contactFile = "data/contact.dat";
        String setupFile = "data/setup.dat";

        // take these objects as arguments in your Manager constructor
        expenseStorage = new Storage(expenseFile);
        incomeStorage = new Storage(incomeFile);
        savingStorage = new Storage(savingFile);
        loanStorage = new Storage(loanFile);
        contactStorage = new Storage(contactFile);
        setupStorage = new Storage(setupFile);

        // Attempt to load existing config
        SetupConfig setupConfig = setupStorage.loadSetupConfig();

        if (setupConfig != null) {
            // If the config exists, read its fields
            isFirstTime = setupConfig.isFirstTime();
            String currencyStr = setupConfig.getCurrencyCode();
            System.out.println("Loaded config: isFirstTime=" + isFirstTime
                    + ", currency=" + currencyStr);
            data = new FinanceData();
            data.setCurrency(currencyStr);
        } else {
            // If file not found or load failed, default to your existing approach
            System.out.println("No setup config found, default to isFirstTime=true, currency=USD");
            isFirstTime = true;
            data = new FinanceData();
            data.setCurrency("USD");
        }

        String currencyStr = data.getCurrency().getCurrencyCode();
        Currency currency = Currency.getInstance(currencyStr);

        expenseManager = new ExpenseManager(data, currencyStr, expenseStorage);     //need to change this part to accept Currency class
        incomeManager = new IncomeManager(data, currencyStr, incomeStorage);
        savingManager = new SavingList(currencyStr);
        budgetManager = new BudgetList(currency);
        loanManager = new LoanManager("defaultUser");
        setUpManager = new SetUpManager(setupStorage);

        // Set integration points in FinanceData.
        data.setExpenseManager(expenseManager);
        data.setIncomeManager(incomeManager);
        data.setSavingsManager(savingManager);
        data.setBudgetManager(budgetManager);
        data.setLoanManager(loanManager);
        data.setSetUpManager(setUpManager);
        // Initialize Analytics module.
        AnalyticsManager analyticsManager = new AnalyticsManager(data);
        data.setAnalyticsManager(analyticsManager);
    }

    /**
     * Runs the main application loop.
     * Continuously reads user commands, parses them, and executes the corresponding actions
     * until the ExitCommand is issued.
     */
    public void run() {
        UI ui = new UI(data);
        System.out.println("welcome to cashflow!");
        System.out.println();
        // Run first-time setup.

        while (!isExit) {
            try {
                if (isFirstTime) {
                    System.out.println("First-time user setup:"
                            + "\n---------------------------------");
                    new SetUpCommand(data, setupStorage).execute();
                    isFirstTime = false;
                    data.setFirstTime(isFirstTime);
                }
                // Start the command-line UI loop.
                ui.run();
                isExit = ui.isExit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
