package expense_income.expense;

import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(String description, double amount, LocalDate date) {
        assert description != null && !description.trim().isEmpty();
        assert amount > 0;
        assert date != null;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDescription(String description) {
        assert description != null && !description.trim().isEmpty() : "Expense description cannot be empty.";
        this.description = description;
    }

    public void setAmount(double amount) {
        assert amount > 0 : "Expense amount must be greater than zero.";
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        assert date != null : "Expense date cannot be null.";
        this.date = date;
    }

    @Override
    public String toString() {
        return description + " - $" + amount + " on " + date;
    }
}
