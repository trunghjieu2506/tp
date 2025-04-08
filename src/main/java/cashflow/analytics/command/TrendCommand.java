package cashflow.analytics.command;
import cashflow.analytics.AnalyticsManager;

import java.time.LocalDate;

public class TrendCommand extends AnalyticGeneralCommand {
    private String type;
    private LocalDate start;
    private LocalDate end;
    private String interval;

    public TrendCommand(String type, LocalDate start, LocalDate end, String interval) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    @Override
    public void execute(AnalyticsManager analytics) {
        if (analytics != null) {
            analytics.showTrendOverTime(type,start,end,interval);
        } else {
            System.out.println("Analytics module is not available.");
        }
    }
}
