package cashflow.analytics.command;

import cashflow.analytics.AnalyticsManager;

public class SpendingInsightCommand extends AnalyticGeneralCommand{
    private int year;
    private int month;

    public SpendingInsightCommand(int month, int year) {
        this.month = month;
        this.year = year;
    }
    @Override
    public void execute(AnalyticsManager analytics) {
        if (analytics != null) {
            analytics.showSpendingInsights(month, year);
        } else {
            System.out.println("Analytics module is not available.");
        }
    }
}
