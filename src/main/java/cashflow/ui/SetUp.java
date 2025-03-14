package cashflow.ui;

import cashflow.model.FinanceData;

import java.util.Scanner;

public class SetUp {
    private FinanceData data;

    public SetUp(FinanceData data) {
        this.data = data;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First-Time Setup:");

        // Set currency.
        System.out.print("Enter your currency (e.g., USD, EUR): ");
        String currency = scanner.nextLine();
        data.setCurrency(currency);

        //        // Set default categories (to be used by Expense/Income module).
        //        System.out.print("Enter default expense/income categories separated by commas: ");
        //        String categoriesLine = scanner.nextLine();
        //        String[] categories = categoriesLine.split(",");
        //        for (String cat : categories) {
        //            data.addCategory(cat.trim());
        //        }

        System.out.println("Setup complete!\n");
    }
}
