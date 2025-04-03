package cashflow.analytics.parser;

import cashflow.analytics.command.AnalyticGeneralCommand;
import cashflow.analytics.command.CommandHandler;
import cashflow.analytics.command.OverviewCommand;
import cashflow.ui.command.Command;

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

    public static AnalyticGeneralCommand parseCommand(String input) {
        String[] command = input.split(" ", 2);
        if (COMMANDS.containsKey(command[0])) {
            //handle method is defined in CommandHandler
            return COMMANDS.get(command[0]).handle(input);
        } else {
            throw new IllegalArgumentException("Unknown command: " + command[0]);
        }
    }
}
