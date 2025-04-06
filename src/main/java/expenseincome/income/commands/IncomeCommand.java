package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Abstract base class for all Income-related commands.
 * Each subclass must implement the execute method.
 */
public abstract class IncomeCommand {
    /**
     * Executes the command using the given IncomeManager.
     *
     * @param manager the IncomeManager instance
     */
    public abstract void execute(IncomeManager manager);
}
