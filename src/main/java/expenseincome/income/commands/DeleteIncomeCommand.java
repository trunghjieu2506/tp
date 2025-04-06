package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Represents a command to delete an income entry from the list by its index.
 */
public class DeleteIncomeCommand extends IncomeCommand {
    private final int index;

    /**
     * Constructs a DeleteIncomeCommand with the given index.
     *
     * @param index The 1-based index of the income entry to be deleted.
     */
    public DeleteIncomeCommand(int index) {
        this.index = index;
    }
    /**
     * Executes the delete income command.
     *
     * @param manager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance should not be null.";
        assert index >= 1 : "Index must be positive (should be checked before calling this method).";

        manager.deleteIncome(index);
    }
}
