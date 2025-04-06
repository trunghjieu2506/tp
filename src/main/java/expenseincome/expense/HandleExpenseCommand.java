package expenseincome.expense;

import java.util.Scanner;

public class HandleExpenseCommand {

    public static void handle(Scanner scanner, ExpenseManager manager) {
        System.out.println("Expense Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Expense Mode.");
                break;
            }

            ExpenseParserResult result = ExpenseCommandParser.parseCommand(input);

            if (result.hasFeedback()) {
                System.out.println(result.getFeedback());
            }

            if (result.hasCommand()) {
                result.getCommand().execute(manager);
            }
        }
    }
}
