package budgetsaving.saving.command;

import budgetsaving.saving.exceptions.SavingException;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.io.IOHandler;
import utils.money.Money;
import java.time.LocalDate;

public class SetSavingCommand implements Command {
    public static final String DATE_FORMAT_ERROR = "Invalid date format, the correct format is: yyyy-MM-dd";

    private SavingManager savingList;
    private String goalName;
    private Money amount;
    private LocalDate deadline;

    public SetSavingCommand(SavingManager savingList, String goalName, Money amount, LocalDate deadline) {
        this.savingList = savingList;
        this.goalName = goalName;
        this.amount = amount;
        this.deadline = deadline;
    }

    @Override
    public void execute() {
        try {
            String message = savingList.setNewSaving(goalName, amount, deadline);
            IOHandler.writeOutput(message);
        } catch (SavingException e) {
            SavingException.writeException(e);
        }
    }
}
