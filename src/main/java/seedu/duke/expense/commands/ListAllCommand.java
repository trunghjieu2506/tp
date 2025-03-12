package seedu.duke.expense.commands;

import seedu.duke.expense.*;
import seedu.duke.income.*;

public class ListAllCommand extends ExpenseCommand {
    private IncomeManager incomeManager;

    public ListAllCommand(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    @Override
    public void execute(ExpenseManager expenseManager) {
        expenseManager.listExpenses();
        incomeManager.listIncomes();
    }
}
