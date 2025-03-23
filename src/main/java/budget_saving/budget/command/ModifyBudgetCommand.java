package budget_saving.budget.command;

import budget_saving.budget.Budget;
import budget_saving.budget.BudgetException;
import cashflow.command.Command;
import cashflow.model.interfaces.BudgetManager;

public class ModifyBudgetCommand implements Command {

    private static final String MODIFY_SUCCESS_MESSAGE = "Successfully modified budget.";

    private BudgetManager budgetManager;
    private int index;
    private double amount;
    private String name;

    private void printSuccess(Budget budget) {
        System.out.println(MODIFY_SUCCESS_MESSAGE);
        System.out.println("The updated budget is: \n"
                            + budget.toString());
    }

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
            printSuccess(budgetManager.getBudget(index));
        } catch (BudgetException e){
            System.err.println(e.getMessage());
        }
    }
}
