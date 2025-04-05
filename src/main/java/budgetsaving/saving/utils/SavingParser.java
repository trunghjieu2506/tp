package budgetsaving.saving.utils;

import budgetsaving.saving.command.ListGoalCommand;
import budgetsaving.saving.command.ContributeGoalCommand;
import budgetsaving.saving.command.SetGoalCommand;
import cashflow.ui.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.money.Money;
import java.time.LocalDate;

public class SavingParser {
    public static Command parseSetGoalCommand(String input, SavingManager savingList)
            throws NumberFormatException {
        SavingAttributes attributes = new SavingAttributes(input);
        String name = attributes.getName();
        Money setAmount = new Money(savingList.getCurrency(), attributes.getAmount());
        LocalDate deadline = attributes.getDeadline();
        return new SetGoalCommand(savingList, name, setAmount, deadline);
    }

    public static Command parseContributeGoalCommand(String input, SavingManager savingList)
            throws NumberFormatException {
        SavingAttributes attributes = new SavingAttributes(input);
        int index = attributes.getIndex();
        Money setAmount = new Money(savingList.getCurrency(), attributes.getAmount());

        return new ContributeGoalCommand(savingList, index, setAmount);
    }

    public static Command parseCheckGoalCommand(SavingManager savingList) {
        // Expected format: check-goal with no extra parameters.
        return new ListGoalCommand(savingList);
    }
}
