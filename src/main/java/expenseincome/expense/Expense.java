package expenseincome.expense;

import cashflow.model.interfaces.Finance;
import utils.money.Money;

import java.time.LocalDate;
/**
 * Represents a single expense entry with description, amount, date, and category.
 * Used as a model for storing financial records in the ExpenseManager.
 */
public class Expense extends Finance {
    private String description;
    private Money amount;
    private LocalDate date;
    private String category;
    /**
     * Constructs a new Expense object.
     *
     * @param description the description of the expense
     * @param amount the amount of the expense as a Money object
     * @param date the date the expense occurred
     * @param category the category this expense falls under
     */
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
