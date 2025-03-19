package budget.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class CheckBudgetCommand implements Command {
    private BudgetManager budgetManager;

    public CheckBudgetCommand(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    @Override
    public void execute() {
        budgetManager.checkBudget();
    }
}

