package loanbook;

import loanbook.commands.LoanCommand;
import loanbook.parsers.LoanCommandParser;

import java.util.Scanner;

public class LoanUI {
    public static void handleLoanCommands(LoanManager loanManager, Scanner scanner, String currency) {
        System.out.println("Loan Mode: Enter commands (type 'exit' to return)");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Loan Mode.");
                break;
            }

            LoanCommand loanCommand = LoanCommandParser.parse(loanManager, scanner, currency, command);
            if (loanCommand != null) {
                loanCommand.execute();
            } else {
                System.out.println("Invalid loan command.");
            }
        }
    }
}
