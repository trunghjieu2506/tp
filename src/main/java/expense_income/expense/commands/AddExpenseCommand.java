package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;

public class AddExpenseCommand extends ExpenseCommand {
    private String description;
    private double amount;

    public AddExpenseCommand(String description, double amount) {
        assert description != null && !description.trim().isEmpty() : "Expense description cannot be empty.";
        assert amount > 0 : "Expense amount must be greater than zero.";

        this.description = description;
        this.amount = amount;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager instance cannot be null.";

        manager.addExpense(description, amount);
    }
}
