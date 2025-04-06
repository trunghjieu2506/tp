package budgetsaving.saving.command;

import budgetsaving.saving.exceptions.SavingException;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.io.IOHandler;
import utils.money.Money;

public class ContributeGoalCommand implements Command {
    private SavingManager savingList;
    private int index;
    private Money amount;

    public ContributeGoalCommand(SavingManager savingList, int index, Money amount) {
        this.savingList = savingList;
        this.index = index;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            String message = savingList.contributeToSaving(index, amount);
            IOHandler.writeOutput(message);
        } catch (SavingException e){
            SavingException.writeException(e);
        }
    }
}
