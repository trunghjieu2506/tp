package expenseincome.income;

import cashflow.model.interfaces.Finance;
import utils.money.Money;

import java.time.LocalDate;
/**
 * Represents a single income record with source, amount, date, and category.
 */
public class Income extends Finance {
    private String source;
    private Money amount;
    private LocalDate date;
    private String category;
    /**
     * Constructs an Income object.
     *
     * @param source   The income source (e.g., Salary, Bonus).
     * @param amount   The monetary amount of income.
     * @param date     The date the income was received.
     * @param category The category the income belongs to (e.g., Job, Gift).
     */
    public Income(String source, Money amount, LocalDate date, String category) {
        assert source != null && !source.trim().isEmpty();
        assert date != null;
        assert category != null && !category.trim().isEmpty();

        this.source = source;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public double getAmount() {
        return amount.getAmount().doubleValue();
    }

    @Override
    public String getType() {
        return "income";
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setSource(String source) {
        assert source != null && !source.trim().isEmpty();
        this.source = source;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        assert date != null;
        this.date = date;
    }

    public void setCategory(String category) {
        assert category != null && !category.trim().isEmpty();
        this.category = category;
    }

    @Override
    public String toString() {
        return source + " - " + amount + " on " + date + " [Category: " + category + "]";
    }
}
