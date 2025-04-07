package expenseincome.expense;

import cashflow.model.interfaces.Finance;
import utils.money.Money;

import java.io.Serial;
import java.time.LocalDate;

public class Expense extends Finance {
    @Serial
    private static final long serialVersionUID = 1L;

    private String description;
    private Money amount;
    private LocalDate date;
    private String category;

    public Expense(String description, Money amount, LocalDate date, String category) {
        assert description != null && !description.trim().isEmpty();
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
        return amount.getAmount().doubleValue();
    }

    @Override
    public String getType() {
        return "expense";
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

    public void setAmount(Money amount) {
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
        return description + " - " + amount + " on " + date + " [Category: " + category + "]";
    }
}
