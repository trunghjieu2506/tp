package expenseincome.income;

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

            IncomeParserResult result = IncomeCommandParser.parseCommand(command);

            if (result.hasFeedback()) {
                System.out.println(result.getFeedback());
            }

            if (result.hasCommand()) {
                result.getCommand().execute(incomeManager);
            }
        }
    }
}
