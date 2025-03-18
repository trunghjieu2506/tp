package cashflow.ui;

import budget_saving.budget.BudgetList;
import budget_saving.budget.command.BudgetGeneralCommand;
import cashflow.command.HelpCommand;
import cashflow.command.OverviewCommand;
import cashflow.model.FinanceData;
import budget_saving.budget.saving.SavingList;
import budget_saving.budget.saving.command.SavingGeneralCommand;
import expense_income.expense.ExpenseCommandParser;
import expense_income.expense.commands.ExpenseCommand;
import expense_income.income.IncomeCommandParser;
import expense_income.income.commands.IncomeCommand;
import expense_income.expense.ExpenseManager;
import expense_income.income.IncomeManager;

import java.util.Scanner;

public class UI {
    private FinanceData data;
    private SavingList savingList;
    private BudgetList budgetList;

    public UI(FinanceData data) {
        this.data = data;
        this.savingList = new SavingList(data.getCurrency());
        this.budgetList = new BudgetList(data.getCurrency());

        this.expenseManager = data.getExpenseManager();
        this.incomeManager = data.getIncomeManager();

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Enter command (type 'help' for commands): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
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
                //added new saving commands
            case "budget_saving/budget/saving":
                new SavingGeneralCommand(input, savingList).execute();
                break;
            case "budget_saving/budget":
                new BudgetGeneralCommand(input, budgetList).execute();
                break;
            case "expense":
                handleExpenseCommands(scanner);
                break;
            case "income":
                handleIncomeCommands(scanner);
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