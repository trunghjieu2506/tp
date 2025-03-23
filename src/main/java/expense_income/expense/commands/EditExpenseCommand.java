package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public class EditExpenseCommand extends ExpenseCommand {
    private int index;
    private String newDescription;
    private double newAmount;

    public EditExpenseCommand(int index, String newDescription, double newAmount) {
        this.index = index;
        this.newDescription = newDescription;
        this.newAmount = newAmount;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager cannot be null.";
        manager.editExpense(index, newDescription, newAmount);
    }
}
