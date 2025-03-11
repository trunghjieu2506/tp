package seedu.duke;

import seedu.duke.expense.*;
import seedu.duke.expense.commands.*;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            Command command = CommandParser.parseCommand(input);

            if (command == null) {
                if (input.equals("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                continue;
            }

            command.execute(manager);
        }

        scanner.close();
    }
}
