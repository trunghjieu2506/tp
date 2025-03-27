package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class DeleteIncomeCommand extends IncomeCommand {
    private int index;

    public DeleteIncomeCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance should not be null.";
        assert index >= 1 : "Index must be positive (should be checked before calling this method).";

        manager.deleteIncome(index);
    }
}
