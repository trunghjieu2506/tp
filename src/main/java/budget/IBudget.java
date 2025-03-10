package budget;

/**
 * Interface for budget features.
 */
public interface IBudget {
    /**
     * Sets the monthly budget.
     * @param amount The budget amount.
     */
    void setBudget(double amount);

    /**
     * Checks and displays the budget details.
     */
    void checkBudget();
}
