package loanbook.loanbook.parsers;

import loanbook.loanbook.interest.Interest;
import loanbook.loanbook.interest.InterestType;

import java.time.Period;

public class InterestParser {
    public static Interest parseInterest(String input) {

        String[] splitBySpace = input.replace('%', ' ').split("\\s+",4);
        try {
            InterestType interestType = InterestType.valueOf(splitBySpace[0].trim());
            double interestRate = Double.parseDouble(splitBySpace[1].trim());
            if (splitBySpace[2].trim().equalsIgnoreCase("per")) {
                return new Interest(interestRate, interestType, parsePeriod(splitBySpace[3].trim()));
            }
        } catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    private static Period parsePeriod(String input) {
        String[] split = input.split(" ");
        String unit;
        int unitsPerPeriod;
        try {
            unitsPerPeriod = Integer.parseInt(split[0]);
            unit = split[1];
        } catch (NumberFormatException e) {
            unitsPerPeriod = 1;
            unit = split[0];
        }
        if (unit.equalsIgnoreCase("year") || unit.equalsIgnoreCase("years")) {
            return Period.ofYears(unitsPerPeriod);
        } else if (unit.equalsIgnoreCase("month") || unit.equalsIgnoreCase("months")) {
            return Period.ofMonths(unitsPerPeriod);
        } else if (unit.equalsIgnoreCase("day") || unit.equalsIgnoreCase("days")) {
            return Period.ofDays(unitsPerPeriod);
        }
        return null;
    }
}
