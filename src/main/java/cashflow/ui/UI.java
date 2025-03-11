package cashflow.ui;

import cashflow.command.HelpCommand;
import cashflow.command.OverviewCommand;
import cashflow.model.FinanceData;

import java.util.Scanner;

public class UI {
    private FinanceData data;

    public UI(FinanceData data) {
        this.data = data;
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
            default:
                System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }
}
