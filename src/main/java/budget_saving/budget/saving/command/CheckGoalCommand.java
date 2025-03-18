package budget_saving.budget.saving.command;

import cashflow.command.Command;
import budget_saving.budget.saving.SavingList;

public class CheckGoalCommand implements Command {
    private SavingList savingList;

    public CheckGoalCommand(SavingList savingList) {
        this.savingList = savingList;
    }

    @Override
    public void execute() {
        System.out.println(savingList.checkGoals());
    }
}

