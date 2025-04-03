package cashflow.ui;

import budgetsaving.budget.BudgetList;
import cashflow.ui.command.HelpCommand;
import cashflow.ui.command.OverviewCommand;
import cashflow.model.FinanceData;
import budgetsaving.saving.SavingList;

import loanbook.LoanUI;
import loanbook.LoanManager;
import loanbook.save.LoanSaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static budgetsaving.budget.command.BudgetGeneralCommand.handleBudgetCommand;
import static budgetsaving.saving.command.SavingGeneralCommand.handleSavingCommand;

import expenseincome.expense.HandleExpenseCommand;
import expenseincome.expense.ExpenseManager;
import expenseincome.income.HandleIncomeCommand;
import expenseincome.income.IncomeManager;

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
                LoanUI.handleLoanCommands(loanManager, scanner, data.getCurrency());
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }
}