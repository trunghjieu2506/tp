package cashflow.ui;

import budgetsaving.budget.BudgetList;
import cashflow.command.HelpCommand;
import cashflow.command.OverviewCommand;
import cashflow.model.FinanceData;
import budgetsaving.saving.SavingList;
import budgetsaving.saving.command.SavingGeneralCommand;

import expenseincome.expense.HandleExpenseCommand;
import expenseincome.income.HandleIncomeCommand;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;

import loanbook.parsers.LoanCommandParser;
import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.save.LoanSaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static budgetsaving.budget.command.BudgetGeneralCommand.handleBudgetCommand;
import static budgetsaving.saving.command.SavingGeneralCommand.handleSavingCommand;

public class UI {
    private FinanceData data;
    private SavingList savingList;
    private BudgetList budgetList;
    private LoanManager loanManager;

    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;

    public UI(FinanceData data) {
        this.data = data;
        this.savingList = new SavingList(data.getCurrency());
        this.budgetList = new BudgetList(data.getCurrency());
        //Can create a username.
        try {
            this.loanManager = LoanSaveManager.readLoanList("GeorgeMiao");
        } catch (FileNotFoundException e) {
            this.loanManager = new LoanManager("GeorgeMiao");
        }

        this.expenseManager = data.getExpenseManager();
        this.incomeManager = data.getIncomeManager();
    }

    //printResult(Result result)

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Enter command (type 'help' for commands): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                try {
                    LoanSaveManager.saveLoanList(loanManager);
                } catch (IOException e) {
                    System.out.println("Unable to update save file");
                }
                System.out.println("Exiting. Goodbye!");
                break;
            }

            switch (input.toLowerCase()) {
            case "help":
                new HelpCommand().execute();
                break;
            case "overview":
                new OverviewCommand(data).execute();
                break;
            case "setup":
                new SetUp(data).run();
                break;
            case "saving":
                //new SavingGeneralCommand(input, savingList).execute();
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
                handleLoanCommands(scanner);
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }


    private void handleLoanCommands(Scanner scanner) {
        System.out.println("Loan Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Loan Mode.");
                break;
            }

            LoanCommand loanCommand = LoanCommandParser.parse(loanManager, scanner, data.getCurrency(), command);
            if (loanCommand != null) {
                loanCommand.execute();
            } else {
                System.out.println("Invalid loan command.");
            }
        }
    }
}