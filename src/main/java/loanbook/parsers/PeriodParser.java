package loanbook.parsers;

import java.time.Period;

public class PeriodParser {
    public static Period parsePeriod(String input) {
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
