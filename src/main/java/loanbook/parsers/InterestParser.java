package loanbook.parsers;

import loanbook.interest.Interest;
import loanbook.interest.InterestType;
import utils.exceptions.NegativeValueException;
import utils.io.IOHandler;

import java.util.Scanner;

import static utils.textcolour.TextColour.RED;

public class InterestParser {
    public static Interest parseInterest(String input) throws NegativeValueException {

        String[] splitBySpace = input.replace('%', ' ').split("\\s+",4);
        try {
            InterestType interestType = InterestType.valueOf(splitBySpace[0].trim().toUpperCase());
            double interestRate = Double.parseDouble(splitBySpace[1].trim());
            if (interestRate <= 0) {
                return null;
            }
            if (splitBySpace[2].trim().equalsIgnoreCase("per")) {
                return new Interest(interestRate, interestType, PeriodParser.parsePeriod(splitBySpace[3].trim()));
            }
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException e) {
            return null;
        }
        return null;
    }

    public static Interest handleInterestInputUI(Scanner scanner) {
        Interest interest;
        System.out.print("Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):\n> ");
        interest = parseInterest(scanner.nextLine());
        while (interest == null) {
            IOHandler.writeOutputWithColour("Invalid interest input!", RED);
            System.out.print("Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):\n> ");
            interest = parseInterest(scanner.nextLine());
        }
        return interest;
    }
}
