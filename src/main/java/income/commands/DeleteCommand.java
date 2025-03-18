package income.commands;

import income.IncomeManager;

public class DeleteCommand extends IncomeCommand {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(IncomeManager manager) {
        manager.deleteIncome(index);
    }
}
