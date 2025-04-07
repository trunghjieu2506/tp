package cashflow.analytics.parser;

import cashflow.analytics.command.AnalyticGeneralCommand;
import cashflow.analytics.command.SpendingBreakDownCommand;
import cashflow.analytics.command.CommandHandler;
import cashflow.analytics.command.HelpCommand;
import cashflow.analytics.command.OverviewCommand;
import cashflow.analytics.command.SpendingInsightCommand;
import cashflow.analytics.command.TrendCommand;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class AnalyticCommandParser {
    private static final Map<String, CommandHandler> COMMANDS = new HashMap<>();

    /**
     * Maps command keywords to their respective command handlers for command instantiation.
     */
    static {
        COMMANDS.put("help", input -> new HelpCommand());
        COMMANDS.put("overview", input -> {
            String[] command = input.split(" ", 2);
            if (command.length < 2) {
                return new OverviewCommand(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            } else {
                try {
                    YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                    return new OverviewCommand(yearMonth.getMonthValue(), yearMonth.getYear());
                }  catch(DateTimeParseException e){
                    throw new DateTimeParseException("Incorrect DateTime Format.\n"
                            +"Follow this syntax: overview [yyyy-mm]", command[1], 0);
                }
            }
        });
        COMMANDS.put("trend", input -> {
            String[] command = input.split(" ", 5);

            if (command.length < 5) {
                throw new Exception("Incorrect syntax. Use trend <data-type> <start-date> <end-date> <interval>\n"
                        + "Example: trend expense 2025-01-24 2025-02-24 monthly");
            }

            if (!command[1].equalsIgnoreCase("expense")
                    && !command[1].equalsIgnoreCase("income")) {
                throw new Exception("Incorrect data-type. Use trend expense or trend income");
            }
            String type = command[1];

            LocalDate start;
            LocalDate end;
            try {
                start = LocalDate.parse(command[2]);
                end = LocalDate.parse(command[3]);
            } catch (DateTimeParseException e) {
                throw new DateTimeParseException("Incorrect Date Format. Please use [yyyy-mm-dd].", command[2], 0);
            }

            if (!end.isAfter(start)) {
                throw new Exception("End date (" + end + ") must be after start date (" + start + ").");
            }

            String interval = command[4];
            if (!interval.equalsIgnoreCase("weekly")
                    && !interval.equalsIgnoreCase("monthly")) {
                throw new Exception("Invalid interval. Use 'weekly' or 'monthly'.");
            }

            return new TrendCommand(type, start, end, interval);
        });

        COMMANDS.put("insight", input -> {
            String[] command = input.split(" ");
            if (command.length < 2) {
                return new SpendingInsightCommand(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            } else {
                try {
                    YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                    return new SpendingInsightCommand(yearMonth.getMonthValue(), yearMonth.getYear());
                }  catch(DateTimeParseException e){
                    throw new DateTimeParseException("Incorrect DateTime Format.\n"
                            +"Follow this syntax: insight [yyyy-mm]", command[1], 0);
                }
            }
    });
        COMMANDS.put("spending-breakdown", input -> {
        String[] command = input.split(" ");
        if (command.length < 2) {
            return new SpendingBreakDownCommand(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        } else {
            try {
                YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                return new SpendingBreakDownCommand(yearMonth.getMonthValue(), yearMonth.getYear());
            }  catch(DateTimeParseException e){
                throw new DateTimeParseException("Incorrect DateTime Format.\n"
                        +"Follow this syntax: spending-breakdown [yyyy-mm]", command[1], 0);
            }
        }
    });
    }
    public static AnalyticGeneralCommand parseCommand(String input) throws Exception {
        String[] command = input.split(" ", 2);
        if (COMMANDS.containsKey(command[0])) {
            return COMMANDS.get(command[0]).handle(input);
        } else {
            throw new IllegalArgumentException("Unknown command. Enter 'help' for available commands");
        }
    }
}
