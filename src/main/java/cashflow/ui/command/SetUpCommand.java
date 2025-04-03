package cashflow.ui.command;

import cashflow.model.FinanceData;

import java.util.Scanner;

public class SetUpCommand implements Command {
    private FinanceData data;

    public SetUpCommand(FinanceData data) {
        this.data = data;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First-Time Setup:");

        // Set currency.
        System.out.print("Enter your currency (e.g., USD, EUR): ");
        String currency = scanner.nextLine();
        data.setCurrency(currency);

        System.out.println("Setup complete!\n");
    }
}
