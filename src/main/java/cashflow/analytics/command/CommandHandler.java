package cashflow.analytics.command;

import cashflow.ui.command.Command;

@FunctionalInterface
public interface CommandHandler {

    AnalyticGeneralCommand handle(String input);
}
