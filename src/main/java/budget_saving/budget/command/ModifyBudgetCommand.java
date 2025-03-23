package budget_saving.budget.command;

import budget_saving.budget.BudgetException;
import cashflow.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class ModifyBudgetCommand implements Command {
    private BudgetManager budgetManager;
    private int index;
    private double amount;
    private String name;

    public ModifyBudgetCommand(BudgetManager budgetManager, int index, double amount, String name) {
        this.index = index;
        this.amount = amount;
        this.name = name;
        this.budgetManager = budgetManager;
    }

    @Override
    public void execute() {
        try{
            budgetManager.modifyBudget(index, name, amount);
        } catch (BudgetException e){
            System.err.println(e.getMessage());
        }
    }
}
