package expenseincome.income.exceptions;
/**
 * Custom exception class for income-related validation errors.
 */
public class IncomeException extends Exception {
    /**
     * Constructs a new IncomeException with a specified message.
     *
     * @param message The error message to display.
     */
    public IncomeException(String message) {
        super(message);
    }
}
