package seedu.duke.income.commands;

import seedu.duke.income.*;

public class ListIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager incomeManager) {
        incomeManager.listIncomes();
    }
}
