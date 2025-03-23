package expense_income.expense;

import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;
    private String category;

    public Expense(String description, double amount, LocalDate date, String category) {
        assert description != null && !description.trim().isEmpty();
        assert amount > 0;
        assert date != null;
        assert category != null && !category.trim().isEmpty();

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
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

    public String getCategory() {
        return category;
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

    public void setCategory(String category) {
        assert category != null && !category.trim().isEmpty();
        this.category = category;
    }

    @Override
    public String toString() {
        return description + " - $" + amount + " on " + date + " [Category: " + category + "]";
    }
}
