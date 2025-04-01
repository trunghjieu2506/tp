package budgetsaving.saving.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;

public class ListGoalCommand implements Command {
    private SavingManager savingList;

    public ListGoalCommand(SavingManager savingList) {
        this.savingList = savingList;
    }

    @Override
    public void execute() {
        System.out.println(savingList.listGoals());
    }
}

