package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public class BottomCategoryExpenseCommand extends ExpenseCommand {
    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager cannot be null.";
        manager.printBottomCategory();
    }
}
