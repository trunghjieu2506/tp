package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;
import java.time.LocalDate;

public class EditExpenseCommand extends ExpenseCommand {
    private int index;
    private String newDescription;
    private double newAmount;
    private LocalDate newDate;

    public EditExpenseCommand(int index, String newDescription, double newAmount, LocalDate newDate) {
        this.index = index;
        this.newDescription = newDescription;
        this.newAmount = newAmount;
        this.newDate = newDate;
    }

    @Override
    public void execute(ExpenseManager manager) {
        assert manager != null : "ExpenseManager cannot be null.";
        manager.editExpense(index, newDescription, newAmount, newDate);
    }
}
