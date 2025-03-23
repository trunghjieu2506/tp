package expense_income.income;

import java.time.LocalDate;

public class Income {
    private String source;
    private double amount;
    private LocalDate date;

    public Income(String source, double amount, LocalDate date) {
        assert source != null && !source.trim().isEmpty();
        assert amount > 0;
        assert date != null;

        this.source = source;
        this.amount = amount;
        this.date = date;
    }

    public String getSource() { return source; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public void setSource(String source) {
        assert source != null && !source.trim().isEmpty();
        this.source = source;
    }

    public void setAmount(double amount) {
        assert amount > 0;
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        assert date != null;
        this.date = date;
    }

    @Override
    public String toString() {
        return source + " - $" + amount + " on " + date;
    }
}
