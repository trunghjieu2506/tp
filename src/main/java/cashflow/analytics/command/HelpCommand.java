package cashflow.analytics.command;

import cashflow.analytics.AnalyticsManager;

public class HelpCommand extends AnalyticGeneralCommand {
    @Override
    public void execute(AnalyticsManager data) {
        System.out.println("Available commands:");
        System.out.println("  help                                                  " +
                "- Display available commands and usage");
        System.out.println("  overview [yyyy-mm]                                    " +
                "- Display monthly financial summary");
        System.out.println("  trend <data-type> <start-date> <end-date> <interval>  " +
                "- Display trends from a range");
        System.out.println("  insight [yyyy-mm]                                     " +
                "- Display spending insights");
        System.out.println("  spending-breakdown [yyyy-mm]                          " +
                "- Display breakdown of spending");
        System.out.println("  exit                                                  " +
                "- Exit the analytic program");
    }

}

