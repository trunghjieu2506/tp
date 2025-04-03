package cashflow.analytics.command;

import cashflow.analytics.parser.AnalyticCommandParser;
import cashflow.model.FinanceData;
import cashflow.ui.command.Command;

import java.util.Scanner;

public abstract class AnalyticGeneralCommand {
    public abstract void execute(FinanceData data);

    public static void handleAnalyticCommand(Scanner scanner, FinanceData data) {
        System.out.println("Analytic Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Analytic Mode.");
                break;
            }
            try{
                AnalyticGeneralCommand c = AnalyticCommandParser.parseCommand(input);
                c.execute(data);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
