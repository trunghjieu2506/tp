package budgetsaving.saving.utils;

import budgetsaving.saving.command.ListGoalCommand;
import budgetsaving.saving.command.ContributeGoalCommand;
import budgetsaving.saving.command.SetGoalCommand;
import cashflow.command.Command;
import cashflow.model.interfaces.SavingManager;
import utils.money.Money;

import java.math.BigDecimal;

public class SavingParser {
    public static Command parseSetGoalCommand(String input, SavingManager savingList)
            throws NumberFormatException {
        // Expected format: set-goal n/GOAL_NAME a/AMOUNT b/BY
        int nIndex = input.indexOf("n/");
        int aIndex = input.indexOf("a/");
        int bIndex = input.indexOf("b/");
        if (nIndex == -1 || aIndex == -1 || bIndex == -1) {
            throw new IllegalArgumentException("Invalid set-goal command format.");
        }
        String name = input.substring(nIndex + 2, aIndex).trim();
        String amountStr = input.substring(aIndex + 2, bIndex).trim();
        String deadline = input.substring(bIndex + 2).trim();
        Money moneyAmount = new Money(savingList.getCurrency(), new BigDecimal(amountStr));
        return new SetGoalCommand(savingList, name, moneyAmount, deadline);
    }

    public static Command parseContributeGoalCommand(String input, SavingManager savingList)
            throws NumberFormatException {
        // Expected format: contribute-goal n/GOAL_NAME a/AMOUNT
        int nIndex = input.indexOf("n/");
        int aIndex = input.indexOf("a/");
        if (nIndex == -1 || aIndex == -1) {
            throw new IllegalArgumentException("Invalid contribute-goal command format.");
        }
        String name = input.substring(nIndex + 2, aIndex).trim();
        String amountStr = input.substring(aIndex + 2).trim();
        Money moneyAmount = new Money(savingList.getCurrency(), new BigDecimal(amountStr));
        return new ContributeGoalCommand(savingList, name, moneyAmount);
    }

    public static Command parseCheckGoalCommand(SavingManager savingList) {
        // Expected format: check-goal with no extra parameters.
        return new ListGoalCommand(savingList);
    }
}
