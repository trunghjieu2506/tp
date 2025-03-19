package budget.command;

import cashflow.ui.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class SetBudgetCommand implements Command {
    private BudgetManager budgetManager;
    private double amount;
    private String name;

    public SetBudgetCommand(BudgetManager budgetManager, String name, double amount) {
        this.budgetManager = budgetManager;
        this.amount = amount;
        this.name = name;
    }

    @Override
    public void execute() {
        budgetManager.setBudget(name, amount);
    }
}
