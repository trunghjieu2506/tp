package cashflow.analytics.parser;

import cashflow.analytics.command.AnalyticGeneralCommand;
import cashflow.analytics.command.CategoryInsightCommand;
import cashflow.analytics.command.CommandHandler;
import cashflow.analytics.command.HelpCommand;
import cashflow.analytics.command.OverviewCommand;
import cashflow.analytics.command.SpendingInsightCommand;
import cashflow.analytics.command.TrendCommand;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
                YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                return new OverviewCommand(yearMonth.getMonthValue(), yearMonth.getYear());
            }
        });
        COMMANDS.put("trend", input -> {
            String[] command = input.split(" ");
            if (command.length < 5) {
                throw new Exception("Incorrect syntax. Use trend <data-type> <start-date> <end-date> <interval>\n"
                        + "Example: trend expense 2025-01-24 2025-02-24 monthly");
            } else {
                String type = command[1];
                LocalDate start = LocalDate.parse(command[2]);
                LocalDate end = LocalDate.parse(command[3]);
                String interval = command[4];
                return new TrendCommand(type, start, end, interval);
            }
        });
        COMMANDS.put("category", input -> {
            String[] command = input.split(" ");
            if (command.length < 2) {
                throw new Exception("Syntax");
            } else {
                YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                return new CategoryInsightCommand(yearMonth.getMonthValue(), yearMonth.getYear());
            }
    });
        COMMANDS.put("spending", input -> {
        String[] command = input.split(" ");
        if (command.length < 2) {
            return new CategoryInsightCommand(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        } else {
            YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
            return new CategoryInsightCommand(yearMonth.getMonthValue(), yearMonth.getYear());
        }
    });
        COMMANDS.put("overview", input -> {
            String[] command = input.split(" ", 2);
            if (command.length < 2) {
                return new OverviewCommand(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            } else {
                YearMonth yearMonth = YearMonth.parse(command[1], DateTimeFormatter.ofPattern("yyyy-MM"));
                return new OverviewCommand(yearMonth.getMonthValue(), yearMonth.getYear());
            }
        });
    }

    public static AnalyticGeneralCommand parseCommand(String input) throws Exception {
        String[] command = input.split(" ", 2);
        if (COMMANDS.containsKey(command[0])) {
            //handle method is defined in CommandHandler
            return COMMANDS.get(command[0]).handle(input);
        } else {
            throw new IllegalArgumentException("Unknown command: " + command[0] + "Enter 'help' for available commands");
        }
    }
}
