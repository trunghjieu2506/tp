package cashflow.analytics.command;

import cashflow.analytics.AnalyticsManager;
import cashflow.analytics.parser.AnalyticCommandParser;

import java.util.Scanner;

/**
 * Represents the abstract base class for all commands within analytics mode.
 * Each concrete command subclass must implement the {@link #execute(AnalyticsManager)} method
 * to perform its specific analytic task.
 */
public abstract class AnalyticGeneralCommand {

    /**
     * Executes the specific action associated with this analytic command.
     * Subclasses must implement this method to perform their unique analytic function
     * using the provided {@link AnalyticsManager}.
     *
     * @param analyticManager The manager instance providing access to analytics methods and data.
     */
    public abstract void execute(AnalyticsManager analyticManager);

    /**
     * Runs the main command loop for analytics mode.
     * It continuously prompts the user with "> ", reads a line of input,
     * attempts to parse it into an {@link AnalyticGeneralCommand} using {@link AnalyticCommandParser},
     * executes the command if parsing is successful, and prints any resulting error messages
     * from parsing or execution.
     *
     * @param scanner to read user input from the console.
     * @param analyticManager {@link AnalyticsManager} instance that provides functionality for
     * executing the analytic commands.
     */
    public static void handleAnalyticCommand(Scanner scanner, AnalyticsManager analyticManager) {
        System.out.println("Analytic Mode: Enter commands (type 'exit' to return, type 'help' for commands)");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Analytic Mode.");
                break;
            }
            try{
                AnalyticGeneralCommand c = AnalyticCommandParser.parseCommand(input);
                c.execute(analyticManager);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
