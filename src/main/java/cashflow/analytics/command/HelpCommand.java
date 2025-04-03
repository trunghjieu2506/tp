package cashflow.analytics.command;

import cashflow.model.FinanceData;

public class HelpCommand extends AnalyticGeneralCommand {
    @Override
    public void execute(FinanceData financeData) {
        System.out.println("Available commands:");
        System.out.println("  help         - Display available commands and usage");
        System.out.println("  overview     - Display monthly financial summary");
        System.out.println("  exit         - Exit the analytic program");
    }

}

