package seedu.duke.expense.commands;

import seedu.duke.expense.*;

public class AddCommand extends Command {
    private String description;
    private double amount;

    public AddCommand(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public void execute(ExpenseManager manager) {
        manager.addExpense(description, amount);
    }
}
