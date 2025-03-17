package saving.command;

import cashflow.command.Command;
import money.Money;
import saving.SavingList;

public class ContributeGoalCommand implements Command {
    private SavingList savingList;
    private String goalName;
    private Money amount;

    public ContributeGoalCommand(SavingList savingList, String goalName, Money amount) {
        this.savingList = savingList;
        this.goalName = goalName;
        this.amount = amount;
    }

    @Override
    public void execute() {
        String message = savingList.contributeGoal(goalName, amount);
        System.out.println(message);
    }
}
