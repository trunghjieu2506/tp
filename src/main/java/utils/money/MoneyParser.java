package utils.money;

import java.util.Currency;
import java.util.Scanner;

public class MoneyParser {
    public static Money handleMoneyInputUI(Scanner scanner, Currency currency, String instruction) {
        double amount;
        while (true) {
            System.out.print(instruction + "\n> ");
            if (scanner.hasNextDouble()) {
                amount = Double.parseDouble(scanner.nextLine());
                break;
            } else {
                System.out.println("Invalid input!");
                scanner.next();
            }
        }
        return new Money(currency, amount);
    }
}
