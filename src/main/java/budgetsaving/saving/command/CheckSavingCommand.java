package budgetsaving.saving.command;

import cashflow.model.interfaces.SavingManager;
import cashflow.ui.command.Command;

public class CheckSavingCommand implements Command {
    private int index;
    private SavingManager savingManager;

    public CheckSavingCommand(int index, SavingManager savingManager) {
        this.index = index;
        this.savingManager = savingManager;
    }

    @Override
    public void execute() {
        savingManager.checkSaving(index);
    }
}
