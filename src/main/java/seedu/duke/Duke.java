package seedu.duke;

import seedu.duke.expense.*;
import seedu.duke.expense.commands.*;
import seedu.duke.income.*;
import seedu.duke.income.commands.*;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();
        IncomeManager incomeManager = new IncomeManager();

        // Pass IncomeManager to ExpenseCommandParser for listing both expenses and incomes
        ExpenseCommandParser.setIncomeManager(incomeManager);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // First check if it's an expense command
            ExpenseCommand expenseCommand = ExpenseCommandParser.parseCommand(input);
            if (expenseCommand != null) {
                expenseCommand.execute(expenseManager);
                continue;
            }

            // If not an expense command, check if it's an income command
            IncomeCommand incomeCommand = IncomeCommandParser.parseCommand(input);
            if (incomeCommand != null) {
                incomeCommand.execute(incomeManager);
                continue;
            }

            // If neither, show an error only once
            if (input.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            System.out.println("Invalid command. Try again.");
        }

        scanner.close();
    }
}
