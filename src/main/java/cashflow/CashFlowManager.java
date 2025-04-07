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

import java.io.File;
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

    private FinanceData data;

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


        data = new FinanceData();
        String currencyStr = "USD"; //data.getCurrency().getCurrencyCode();
        Currency currency = Currency.getInstance(currencyStr);

        expenseManager = new ExpenseManager(data, currencyStr, expenseStorage);     //need to change this part to accept Currency class
        incomeManager = new IncomeManager(data, currencyStr, incomeStorage);
        savingManager = new SavingList(currencyStr);
        budgetManager = new BudgetList(currency);
        this.loanManager = new LoanManager("defaultUser");

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
    private boolean isExit = false;
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
                    new SetUpCommand(data).execute();
                    isFirstTime = false;
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
