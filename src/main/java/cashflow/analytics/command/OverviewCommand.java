package cashflow.analytics.command;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;
import cashflow.ui.command.Command;

public class OverviewCommand extends AnalyticGeneralCommand {
    private int year;
    private int month;

    public OverviewCommand(int month, int year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public void execute(FinanceData data) {
        AnalyticsManager analytics = data.getAnalyticsManager();
        if (analytics != null) {
            System.out.println(analytics.getMonthlySummary(month, year));
        } else {
            System.out.println("Analytics module is not available.");
        }
    }
}
