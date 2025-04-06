package budgetsaving.saving.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.io.IOHandler;

public class ListSavingsCommand implements Command {
    private SavingManager savingList;

    public ListSavingsCommand(SavingManager savingList) {
        this.savingList = savingList;
    }

    @Override
    public void execute() {
        IOHandler.writeOutput(savingList.listSavings());
    }
}

