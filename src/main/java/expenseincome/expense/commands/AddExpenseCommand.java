package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;
import java.time.LocalDate;

public class AddExpenseCommand extends ExpenseCommand {
    private String description;
    private double amount;
    private LocalDate date;
    private String category;

    public AddExpenseCommand(String description, double amount, LocalDate date, String category) {
        assert description != null && !description.trim().isEmpty() : "Expense description cannot be empty.";
        assert amount > 0 : "Expense amount must be greater than zero.";
        assert date != null : "Expense date cannot be null.";
        assert category != null : "Expense category cannot be null.";

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager instance cannot be null.";

        manager.addExpense(description, amount, date, category);
    }
}
