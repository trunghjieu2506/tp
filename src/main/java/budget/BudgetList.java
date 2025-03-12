package budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetList {
    private List<IBudget> budgets;

    public BudgetList() {
        budgets = new ArrayList<>();
    }

    public void addBudget(IBudget budget) throws BudgetException {
        if (budget == null) {
            throw new BudgetException("Cannot add a null budget.");
        }
        budgets.add(budget);
    }

    public void removeBudget(IBudget budget) throws BudgetException {
        if (!budgets.contains(budget)) {
            throw new BudgetException("Budget not found in the list.");
        }
        budgets.remove(budget);
    }

    public IBudget getBudget(int index) throws BudgetException {
        if (index < 0 || index >= budgets.size()) {
            throw new BudgetException("Budget index out of range.");
        }
        return budgets.get(index);
    }

    public List<IBudget> getAllBudgets() {
        return budgets;
    }

    public void listBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("No budgets available.");
            return;
        }
        for (int i = 0; i < budgets.size(); i++) {
            System.out.println("Budget " + (i + 1) + ": " + budgets.get(i).toString());
        }
    }
}

