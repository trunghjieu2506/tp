package seedu.duke.expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String description, double amount) {
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
        if (index >= 1 && index <= expenses.size()) {
            Expense removed = expenses.remove(index - 1);
            System.out.println("Deleted: " + removed);
        } else {
            System.out.println("Invalid expense number.");
        }
    }
}
