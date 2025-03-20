package expense_income.expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String description, double amount) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Expense description cannot be empty.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than zero.");
        }

        Expense expense = new Expense(description, amount);
        expenses.add(expense);
        System.out.println("Added: " + expense);
    }


    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("Expense List:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    public void deleteExpense(int index) {
        if (index < 1 || index > expenses.size()) {
            throw new IllegalArgumentException("Invalid index: must be between 1 and " + expenses.size());
        }
        Expense removed = expenses.remove(index - 1);
        System.out.println("Deleted: " + removed);
    }


    public int getExpenseCount() {
        return expenses.size();
    }

    public Expense getExpense(int i) {
        return expenses.get(i);
    }
}
