package cashflow.ui;

import cashflow.ui.command.HelpCommand;
import cashflow.model.FinanceData;
import cashflow.model.setup.SetUpCommand;
import expenseincome.expense.HandleExpenseCommand;
import expenseincome.income.HandleIncomeCommand;

import loanbook.ui.LoanUI;
import loanbook.save.LoanSaveManager;
import utils.io.IOHandler;

import java.io.IOException;
import java.util.Scanner;

import static budgetsaving.budget.command.BudgetGeneralCommand.handleBudgetCommand;
import static budgetsaving.saving.command.SavingGeneralCommand.handleSavingCommand;
import static cashflow.analytics.command.AnalyticGeneralCommand.handleAnalyticCommand;

public class UI {
    private FinanceData data;
    private boolean isExit = false;

    public UI(FinanceData data) {
        this.data = data;
    }

    public boolean isExit() {
        return isExit;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        IOHandler ioHandler = new IOHandler(scanner);
        String input;

        while (true) {
            IOHandler.writeOutputNoLn("Enter command (type 'help' for commands): ");
            input = ioHandler.readInput();

            if (input.equalsIgnoreCase("exit")) {
                try {
                    LoanSaveManager.saveLoanList(data.getLoanManager());
                } catch (IOException e) {
                    IOHandler.writeOutput("Unable to update save file");
                }
                IOHandler.writeOutput("Exiting. Goodbye!");
                this.isExit = true;
                break;
            }
            switch (input.toLowerCase().trim()) {
            case "help":
                new HelpCommand().execute();
                break;
            case "setup":
                new SetUpCommand(data, data.getSetUpManager().getSetupStorage()).execute();
                break;
            case "analytic":
                handleAnalyticCommand(scanner, data.getAnalyticsManager());
                break;
            case "saving":
                handleSavingCommand(scanner, data.getSavingsManager());
                break;
            case "budget":
                handleBudgetCommand(scanner, data.getBudgetManager());
                break;
            case "expense":
                HandleExpenseCommand.handle(scanner, data.getExpenseManager());
                break;
            case "income":
                HandleIncomeCommand.handle(scanner, data.getIncomeManager());
                break;
            case "loan":
                LoanUI.handleLoanCommands(data.getLoanManager(), scanner, data.getCurrency());
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }
}
