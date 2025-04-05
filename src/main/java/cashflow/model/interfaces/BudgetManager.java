package cashflow.model.interfaces;

import budgetsaving.budget.Budget;
import budgetsaving.budget.exceptions.BudgetException;
import expenseincome.expense.Expense;

import java.time.LocalDate;

/**
 * Interface for budget features.
 */
public interface BudgetManager {

    void setBudget(String name, double amount, LocalDate endDate, String category);

    Budget getBudget(int index) throws BudgetException;

    void listBudgets();

    void deductFromBudget(int index, double amount);

    boolean deductBudgetFromExpense(Expense expense);

    void addToBudget(int index, double amount);

    void checkBudget(int index);

    void modifyBudget(int index, String name, double amount, LocalDate endDate, String category)
            throws BudgetException;
}
