package budget_saving.budget.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class CheckBudgetCommand implements Command {
    private BudgetManager budgetManager;
    private int index;

    public CheckBudgetCommand(int index, BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
        this.index = index;
    }

    @Override
    public void execute() {
        budgetManager.checkBudget(index);
    }
}
