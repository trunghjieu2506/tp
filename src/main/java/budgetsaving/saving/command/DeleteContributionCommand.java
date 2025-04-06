package budgetsaving.saving.command;

import budgetsaving.saving.exceptions.SavingException;
import cashflow.model.interfaces.SavingManager;
import cashflow.ui.command.Command;

public class DeleteContributionCommand implements Command {
    private int index_s;
    private int index_c;
    private SavingManager savingManager;

    public DeleteContributionCommand(int index_s, int index_c, SavingManager savingList) {
        this.index_s = index_s;
        this.index_c = index_c;
        this.savingManager = savingList;
    }

    public void execute() {
        try {
            savingManager.deleteContribution(index_s, index_c);
        } catch (SavingException e){
            SavingException.writeException(e);
        }
    }
}
