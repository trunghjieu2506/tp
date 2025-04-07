package cashflow.analytics.command;

import cashflow.analytics.AnalyticsManager;
import cashflow.analytics.parser.AnalyticCommandParser;
import cashflow.model.FinanceData;

import java.util.Scanner;

public abstract class AnalyticGeneralCommand {
    public abstract void execute(AnalyticsManager analyticManager);

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
