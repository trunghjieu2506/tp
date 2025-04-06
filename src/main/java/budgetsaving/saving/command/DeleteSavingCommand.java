package budgetsaving.saving.command;

import budgetsaving.saving.Saving;
import budgetsaving.saving.exceptions.SavingException;
import budgetsaving.saving.exceptions.SavingRuntimeException;
import cashflow.model.interfaces.SavingManager;
import cashflow.ui.command.Command;
import utils.io.IOHandler;

public class DeleteSavingCommand implements Command {
    private int index;
    private SavingManager savingManager;

    public DeleteSavingCommand(int index, SavingManager savingManager) {
        this.index = index;
        this.savingManager = savingManager;
    }

    @Override
    public void execute(){
        try{
            savingManager.deleteSaving(index);
        } catch (SavingRuntimeException e){
            SavingException.writeException(e);
        }
    }
}
