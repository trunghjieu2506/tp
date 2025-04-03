package cashflow.analytics.command;

@FunctionalInterface
public interface CommandHandler {

    AnalyticGeneralCommand handle(String input);
}
