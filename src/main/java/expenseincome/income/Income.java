package expenseincome.income;

import cashflow.model.interfaces.Finance;
import utils.money.Money;

import java.time.LocalDate;

public class Income extends Finance {
    private String source;
    private Money amount;
    private LocalDate date;
    private String category;

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

    public Money getAmount() {
        return amount;
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
