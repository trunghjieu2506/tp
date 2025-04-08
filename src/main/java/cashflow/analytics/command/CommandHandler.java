package cashflow.analytics.command;

/**
 * Represents a functional interface responsible for handling the parsing logic
 * for a specific analytic command keyword.
 * This interface is  used within the {@link cashflow.analytics.parser.AnalyticCommandParser}
 * to map command strings to their parsing logic, via lambda expressions.
 *
 * @see cashflow.analytics.parser.AnalyticCommandParser
 * @see AnalyticGeneralCommand
 */
@FunctionalInterface
public interface CommandHandler {

    AnalyticGeneralCommand handle(String input) throws Exception;
}
