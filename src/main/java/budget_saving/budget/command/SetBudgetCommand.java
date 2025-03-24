package budget_saving.budget.command;

import cashflow.command.Command;
import cashflow.model.interfaces.BudgetManager;

import java.time.LocalDate;

public class SetBudgetCommand implements Command {
    private BudgetManager budgetManager;
    private double amount;
    private String name;
    private LocalDate endDate;

    public SetBudgetCommand(BudgetManager budgetManager, String name, double amount) {
        this.budgetManager = budgetManager;
        this.amount = amount;
        this.name = name;
    }

    public SetBudgetCommand(BudgetManager budgetManager, String name, double amount, LocalDate endDate) {
        this.budgetManager = budgetManager;
        this.amount = amount;
        this.name = name;
        this.endDate = endDate;
    }

    @Override
    public void execute() {
        budgetManager.setBudget(name, amount);
    }
}
