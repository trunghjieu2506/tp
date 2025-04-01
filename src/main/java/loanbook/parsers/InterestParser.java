package loanbook.parsers;

import loanbook.interest.Interest;
import loanbook.interest.InterestType;

import java.util.Scanner;

public class InterestParser {
    public static Interest parseInterest(String input) {

        String[] splitBySpace = input.replace('%', ' ').split("\\s+",4);
        try {
            InterestType interestType = InterestType.valueOf(splitBySpace[0].trim().toUpperCase());
            double interestRate = Double.parseDouble(splitBySpace[1].trim());
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
            System.out.println("Invalid interest input format!");
            System.out.print("Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):\n> ");
            interest = parseInterest(scanner.nextLine());
        }
        return interest;
    }
}
