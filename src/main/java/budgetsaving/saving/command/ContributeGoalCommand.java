package budgetsaving.saving.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.money.Money;

public class ContributeGoalCommand implements Command {
    private SavingManager savingList;
    private int index;
    private Money amount;

    public ContributeGoalCommand(SavingManager savingList, int index, Money amount) {
        this.savingList = savingList;
        this.index = index;
        this.amount = amount;
    }

    @Override
    public void execute() {
        String message = savingList.contributeToSaving(index, amount);
        System.out.println(message);
    }
}
