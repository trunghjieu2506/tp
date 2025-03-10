package savingGoal;

/**
 * Interface for savings goal operations.
 */
public interface ISavingGoal {
    /**
     * Sets a new savings goal.
     * @param name The goal name.
     * @param amount The target amount.
     * @param deadline The deadline for achieving the goal.
     */
    void setGoal(String name, double amount, String deadline);

    /**
     * Contributes an amount to a specific savings goal.
     * @param name The goal name.
     * @param amount The contribution amount.
     */
    void contribute(String name, double amount);

    /**
     * Checks and displays all savings goals.
     */
    void checkGoals();
}

