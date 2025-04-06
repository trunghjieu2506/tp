package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Represents a command to list all recorded income entries in the system.
 */
public class ListIncomeCommand extends IncomeCommand {
    /**
     * Executes the command by listing all income records.
     *
     * @param incomeManager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager incomeManager) {
        assert incomeManager != null : "IncomeManager instance cannot be null.";
        incomeManager.listIncomes();
    }
}
