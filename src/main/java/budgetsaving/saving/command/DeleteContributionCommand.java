package budgetsaving.saving.command;

import budgetsaving.saving.exceptions.SavingException;
import cashflow.model.interfaces.SavingManager;
import cashflow.ui.command.Command;

public class DeleteContributionCommand implements Command {
    private int indexS;
    private int indexC;
    private SavingManager savingManager;

    public DeleteContributionCommand(int indexS, int indexC, SavingManager savingList) {
        this.indexS = indexS;
        this.indexC = indexC;
        this.savingManager = savingList;
    }

    public void execute() {
        try {
            savingManager.deleteContribution(indexS, indexC);
        } catch (SavingException e){
            SavingException.writeException(e);
        }
    }
}
