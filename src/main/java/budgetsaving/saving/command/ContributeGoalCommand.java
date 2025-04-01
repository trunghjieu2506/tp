package budgetsaving.saving.command;

import cashflow.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.money.Money;

public class ContributeGoalCommand implements Command {
    private SavingManager savingList;
    private String goalName;
    private Money amount;

    public ContributeGoalCommand(SavingManager savingList, String goalName, Money amount) {
        this.savingList = savingList;
        this.goalName = goalName;
        this.amount = amount;
    }

    @Override
    public void execute() {
        String message = savingList.contributeToSaving(goalName, amount);
        System.out.println(message);
    }
}
