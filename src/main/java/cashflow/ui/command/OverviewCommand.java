package cashflow.ui.command;
import cashflow.analytics.AnalyticsManager;
import cashflow.model.FinanceData;

public class OverviewCommand implements Command {
    private FinanceData data;

    public OverviewCommand(FinanceData data) {
        this.data = data;
    }

    @Override
    public void execute() {
        AnalyticsManager analytics = data.getAnalyticsManager();
        if (analytics != null) {
            System.out.println(analytics.getFinancialSummary());
            analytics.provideSuggestions();
        } else {
            System.out.println("Analytics module is not available.");
        }
    }
}
