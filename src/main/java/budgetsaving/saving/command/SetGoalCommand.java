package budgetsaving.saving.command;

import cashflow.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.money.Money;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SetGoalCommand implements Command {
    public static final String DATE_FORMAT_ERROR = "Invalid date format, the correct format is: yyyy-MM-dd";

    private SavingManager savingList;
    private String goalName;
    private Money amount;
    private LocalDate deadline;

    public SetGoalCommand(SavingManager savingList, String goalName, Money amount, LocalDate deadline) {
        this.savingList = savingList;
        this.goalName = goalName;
        this.amount = amount;
        this.deadline = deadline;
    }

    @Override
    public void execute() throws DateTimeParseException {
        String message = savingList.setNewSaving(goalName, amount, deadline);
        System.out.println(message);
    }
}
