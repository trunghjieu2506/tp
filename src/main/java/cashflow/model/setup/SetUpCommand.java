package cashflow.model.setup;

import cashflow.model.FinanceData;
import cashflow.model.storage.Storage;
import cashflow.ui.command.Command;

import java.util.Scanner;

public class SetUpCommand implements Command {
    private FinanceData data;
    private Storage setupStorage;

    public SetUpCommand(FinanceData data, Storage setupStorage) {
        this.data = data;
        this.setupStorage = setupStorage;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        // Set currency.
        while (true) {
            System.out.print("Enter your currency (e.g., USD, EUR): ");
            String currencyInput = scanner.nextLine().trim();
            try {
                data.setCurrency(currencyInput);
                System.out.println("Currency set to: " + data.getCurrency().getCurrencyCode());
                String currencyStr = data.getCurrency().getCurrencyCode();
                SetupConfig newConfig = new SetupConfig(data.isFirstTime(), currencyStr);
                setupStorage.saveSetupConfig(newConfig);
                break; // exit the loop if valid
            } catch (IllegalArgumentException e) {
                // Print the error and continue the loop to re-prompt
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Setup complete!\n");
    }
}
