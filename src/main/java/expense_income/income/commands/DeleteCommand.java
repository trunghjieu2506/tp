package expense_income.income.commands;

import expense_income.income.IncomeManager;

public class DeleteCommand extends IncomeCommand {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance should not be null.";
        assert index >= 1 : "Index must be positive (should be checked before calling this method).";

        manager.deleteIncome(index);
    }
}
