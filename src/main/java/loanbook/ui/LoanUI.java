package loanbook.ui;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;
import loanbook.parsers.LoanCommandParser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the input and output of loan related information.
 */
public class LoanUI {
    /**
     * Runs continuously in loan mode
     * @param loanManager the <code>LoanManager</code> that the commands operates on.
     * @param scanner the <code>Scanner</code> that reads inputs.
     * @param currency the currency that the loanManager are recorded in.
     */
    public static void handleLoanCommands(LoanManager loanManager, Scanner scanner, Currency currency) {
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
                System.out.println("Invalid loan command. Type \"help\" to view available commands.");
            }
        }
    }

    /**
     * Convert an <code>ArrayList</code> of <code>Loan</code>s to a ready-to-print <code>String</code>.
     * @param loans the <code>ArrayList</code> of <code>Loans</code> to be printed.
     * @return the converted <code>String</code>.
     */
    public static String forPrint(ArrayList<Loan> loans) {
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (Loan loan : loans) {
            output.append("[" + i + "] ").append(loan.basicInfo()).append('\n');
            i++;
        }
        return output.toString();
    }

    /**
     * Converts a <code>Map</code> of currency and amount to a ready-to-print <code>String</code>. Each map is printed
     *     in a new line.
     * @param maps the <code>Map</code> to be printed.
     * @return the converted <code>String</code>.
     */
    public static String forPrint(Map<Currency, BigDecimal> maps) {
        StringBuilder output = new StringBuilder();
        for (Currency currency : maps.keySet()) {
            output.append(currency.getCurrencyCode()).append(maps.get(currency)).append('\n');
        }
        return output.toString();
    }
}
