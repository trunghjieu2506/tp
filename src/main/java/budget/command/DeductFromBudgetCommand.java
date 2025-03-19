package budget.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class DeductFromBudgetCommand implements Command {
    private BudgetManager budgetManager;
    private int index;
    private double amount;

    public DeductFromBudgetCommand(BudgetManager budgetManager, int index, double amount) {
        this.budgetManager = budgetManager;
        this.index = index - 1;
        this.amount = amount;
    }

    @Override
    public void execute() {
        budgetManager.deductFromBudget(index, amount);
    }
}

