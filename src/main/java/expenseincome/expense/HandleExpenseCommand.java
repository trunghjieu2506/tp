package expenseincome.expense;

import expenseincome.expense.commands.ExpenseCommand;
import java.util.Scanner;

public class HandleExpenseCommand {

    public static void handle(Scanner scanner, ExpenseManager expenseManager) {
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
}