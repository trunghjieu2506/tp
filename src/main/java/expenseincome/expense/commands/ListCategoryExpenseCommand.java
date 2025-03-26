package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;

public class ListCategoryExpenseCommand extends ExpenseCommand {
    private String category;

    public ListCategoryExpenseCommand(String category) {
        this.category = category;
    }

    @Override
    public void execute(ExpenseManager expenseManager) {
        assert expenseManager != null : "ExpenseManager cannot be null.";
        expenseManager.listExpensesByCategory(category);
    }
}
