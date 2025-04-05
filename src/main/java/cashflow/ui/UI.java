package cashflow.ui;

import budgetsaving.budget.BudgetList;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.SavingManager;
import cashflow.ui.command.HelpCommand;
import cashflow.model.FinanceData;
import budgetsaving.saving.SavingList;
import cashflow.ui.command.SetUpCommand;
import expenseincome.expense.ExpenseCommandParser;
import expenseincome.expense.ExpenseManager;
import expenseincome.expense.commands.ExpenseCommand;
import expenseincome.income.IncomeCommandParser;
import expenseincome.income.IncomeManager;

import expenseincome.income.commands.IncomeCommand;
import loanbook.LoanUI;
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
        //Can create a username.
//        try {
//            this.loanManager = LoanSaveManager.readLoanList("GeorgeMiao");
//        } catch (FileNotFoundException e) {
//            this.loanManager = new LoanManager("GeorgeMiao");
//        }
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
                //new SavingGeneralCommand(input, savingList).execute();
                handleSavingCommand(scanner, savingList);
                break;
            case "budget":
                handleBudgetCommand(scanner, budgetList);
                break;
            case "expense":
                handleExpenseCommands(scanner);
                break;
            case "income":
                handleIncomeCommands(scanner);
                break;
            case "loan":
                LoanUI.handleLoanCommands(loanManager, scanner, "USD");
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }

    private void handleExpenseCommands(Scanner scanner) {
        System.out.println("Expense Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Expense Mode.");
                break;
            }

            ExpenseCommand expenseCommand = ExpenseCommandParser.parseCommand(command);
            if (expenseCommand != null) {
                expenseCommand.execute(expenseManager);
            } else {
                System.out.println("Invalid expense command.");
            }
        }
    }

    private void handleIncomeCommands(Scanner scanner) {
        System.out.println("Income Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Income Mode.");
                break;
            }

            IncomeCommand incomeCommand = IncomeCommandParser.parseCommand(command);
            if (incomeCommand != null) {
                incomeCommand.execute(incomeManager);
            } else {
                System.out.println("Invalid income command.");
            }
        }
    }
}