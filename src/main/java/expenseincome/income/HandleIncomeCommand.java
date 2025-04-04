package expenseincome.income;

import expenseincome.income.commands.IncomeCommand;

import java.util.Scanner;

public class HandleIncomeCommand {

    public static void handle(Scanner scanner, IncomeManager incomeManager) {
        System.out.println("Income Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Income Mode.");
                break;
            }

            IncomeCommand incomeCommand = IncomeCommandParser.parseCommand(command);
            if (incomeCommand != null) {
                incomeCommand.execute(incomeManager);
            } else {
                System.out.println("Invalid income command.");
            }
        }
    }
}
