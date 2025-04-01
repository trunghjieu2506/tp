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
    private String deadline;

    public SetGoalCommand(SavingManager savingList, String goalName, Money amount, String deadline) {
        this.savingList = savingList;
        this.goalName = goalName;
        this.amount = amount;
        this.deadline = deadline;
    }

    @Override
    public void execute() throws DateTimeParseException {
        LocalDate deadlineDate = LocalDate.parse(deadline);
        String message = savingList.setNewSaving(goalName, amount, deadlineDate);
        System.out.println(message);
    }
}
