package budget_saving.saving.command;

import cashflow.ui.command.Command;
import budget_saving.saving.SavingList;

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

