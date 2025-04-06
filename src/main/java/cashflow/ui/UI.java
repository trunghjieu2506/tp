package cashflow.ui;

import budgetsaving.budget.BudgetList;
import cashflow.ui.command.HelpCommand;
import cashflow.model.FinanceData;
import budgetsaving.saving.SavingList;
import cashflow.ui.command.SetUpCommand;
import expenseincome.expense.ExpenseManager;
import expenseincome.expense.HandleExpenseCommand;
import expenseincome.income.HandleIncomeCommand;
import expenseincome.income.IncomeManager;

import loanbook.ui.LoanUI;
import loanbook.LoanManager;
import loanbook.save.LoanSaveManager;
import utils.io.IOHandler;

import java.io.IOException;
import java.util.Scanner;

import static budgetsaving.budget.command.BudgetGeneralCommand.handleBudgetCommand;
import static budgetsaving.saving.command.SavingGeneralCommand.handleSavingCommand;
import static cashflow.analytics.command.AnalyticGeneralCommand.handleAnalyticCommand;

public class UI {
    private FinanceData data;
    private SavingList savingList;
    private BudgetList budgetList;
    private LoanManager loanManager;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;

    public UI(FinanceData data) {
        this.data = data;
        this.savingList = data.getSavingsManager();
        this.budgetList = data.getBudgetManager();
        this.loanManager = data.getLoanManager();
        this.expenseManager = data.getExpenseManager();
        this.incomeManager = data.getIncomeManager();
    }

    //printResult(Result result)

    public void run() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        IOHandler ioHandler = new IOHandler(scanner);
        String input;

        while (true) {
            IOHandler.writeOutputNoLn("Enter command (type 'help' for commands): ");
            input = ioHandler.readInput();

            if (input.equalsIgnoreCase("exit")) {
                try {
                    LoanSaveManager.saveLoanList(loanManager);
                } catch (IOException e) {
                    IOHandler.writeOutput("Unable to update save file");
                }
                IOHandler.writeOutput("Exiting. Goodbye!");
                break;
            }
            switch (input.toLowerCase()) {
            case "help":
                new HelpCommand().execute();
                break;
            case "setup":
                new SetUpCommand(data).execute();
                break;
            case "analytic":
                handleAnalyticCommand(scanner, data);
                break;
            case "saving":
                handleSavingCommand(scanner, savingList);
                break;
            case "budget":
                handleBudgetCommand(scanner, budgetList);
                break;
            case "expense":
                HandleExpenseCommand.handle(scanner, expenseManager);
                break;
            case "income":
                HandleIncomeCommand.handle(scanner, incomeManager);
                break;
            case "loan":
                LoanUI.handleLoanCommands(loanManager, scanner, data.getCurrency());
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }
}