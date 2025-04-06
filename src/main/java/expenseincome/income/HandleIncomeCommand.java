package expenseincome.income;

import java.util.Scanner;
/**
 * Acts as the logic gateway for handling parsed income commands.
 * This class receives the parsed result and executes the command using the IncomeManager.
 */
public class HandleIncomeCommand {
    /**
     * Executes the command if valid, or prints feedback if there's an error.
     *
     * @param scanner The user input string from CLI.
     * @param incomeManager The IncomeManager that handles business logic.
     */
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
