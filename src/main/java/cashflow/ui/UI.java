package cashflow.ui;

import budget.BudgetList;
import budget.command.BudgetGeneralCommand;
import cashflow.ui.command.HelpCommand;
import cashflow.ui.command.OverviewCommand;
import cashflow.model.FinanceData;
import saving.SavingList;
import saving.command.SavingGeneralCommand;

import java.util.Scanner;

public class UI {
    private FinanceData data;
    private SavingList savingList;
    private BudgetList budgetList;

    public UI(FinanceData data) {
        this.data = data;
        this.savingList = new SavingList(data.getCurrency());
        this.budgetList = new BudgetList(data.getCurrency());
    }

    public void welcome() {
        // Display a welcome message with an initial financial summary.
        System.out.println("welcome to cashflow!");
        System.out.println(data.getAnalyticsManager().getFinancialSummary());
        System.out.println();
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
            case "saving":
                new SavingGeneralCommand(input, savingList).execute();
                // new SavingGeneralCommand(, data).execute();
                break;
            case "budget":
                new BudgetGeneralCommand(input, budgetList).execute();
                break;
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }
}
