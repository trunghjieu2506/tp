package utils.money;

import java.util.Currency;
import java.util.Scanner;

public class MoneyParser {
    public static Money parse(String input) throws IllegalArgumentException {
        String[] split = input.split(" ");
        if (split.length <= 1) {
            return null;
        }
        Currency currency = Currency.getInstance(split[0]);
        double amount = Double.parseDouble(split[1]);
        return new Money(currency, amount);
    }

    public static Money handleMoneyInputUI(Scanner scanner, Currency currency, String instruction) {
        double amount;
        while (true) {
            System.out.print(instruction + "\n> ");
            String input = scanner.nextLine();
            try {
                amount = Double.parseDouble(input);
                break;
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid input");
            }
        }
        return new Money(currency, amount);
    }
}
