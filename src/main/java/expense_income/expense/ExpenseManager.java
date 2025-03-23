package expense_income.expense;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String description, double amount, LocalDate date) {
        try {
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("Expense description cannot be empty.");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Expense amount must be greater than zero.");
            }

            Expense expense = new Expense(description, amount, date);
            expenses.add(expense);
            System.out.println("Added: " + expense);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add expense. " + e.getMessage());
        }
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
        try {
            if (index < 1 || index > expenses.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + expenses.size());
            }
            Expense removed = expenses.remove(index - 1);
            System.out.println("Deleted: " + removed);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to delete expense. " + e.getMessage());
        }
    }

    public void editExpense(int index, String newDescription, double newAmount, LocalDate newDate) {
        try {
            if (index < 1 || index > expenses.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + expenses.size());
            }
            if (newDescription == null || newDescription.trim().isEmpty()) {
                throw new IllegalArgumentException("New description cannot be empty.");
            }
            if (newAmount <= 0) {
                throw new IllegalArgumentException("New amount must be greater than zero.");
            }

            Expense expense = expenses.get(index - 1);
            expense.setDescription(newDescription);
            expense.setAmount(newAmount);
            expense.setDate(newDate);
            System.out.println("Updated: " + expense);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to edit expense. " + e.getMessage());
        }
    }

    public int getExpenseCount() {
        return expenses.size();
    }

    public Expense getExpense(int i) {
        return expenses.get(i);
    }
}
