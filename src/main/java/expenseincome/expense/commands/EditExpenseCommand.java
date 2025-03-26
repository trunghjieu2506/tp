package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;
import java.time.LocalDate;

public class EditExpenseCommand extends ExpenseCommand {
    private int index;
    private String newDescription;
    private double newAmount;
    private LocalDate newDate;
    private String newCategory;

    public EditExpenseCommand(int index, String newDescription, double newAmount, LocalDate newDate, String newCategory) {
        this.index = index;
        this.newDescription = newDescription;
        this.newAmount = newAmount;
        this.newDate = newDate;
        this.newCategory = newCategory;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager cannot be null.";
        manager.editExpense(index, newDescription, newAmount, newDate, newCategory);
    }
}
