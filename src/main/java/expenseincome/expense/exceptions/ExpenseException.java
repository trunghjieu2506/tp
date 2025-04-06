package expenseincome.expense.exceptions;
/**
 * Custom exception for handling errors in expense-related operations.
 * This exception is thrown when invalid input or operations occur
 * in the expense subsystem such as adding invalid data or accessing
 * out-of-bound indexes.
 */
public class ExpenseException extends Exception {
    /**
     * Constructs a new ExpenseException with the specified detail message.
     *
     * @param message the detail message
     */
    public ExpenseException(String message) {
        super(message);
    }
}
