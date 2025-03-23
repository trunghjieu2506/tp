package expense_income.income;

public class Income {
    private String source;
    private double amount;

    public Income(String source, double amount) {
        assert source != null && !source.trim().isEmpty() : "Income source cannot be null or empty";
        assert amount > 0 : "Income amount must be greater than zero";

        this.source = source;
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public double getAmount() {
        return amount;
    }

    public void setSource(String source) {
        assert source != null && !source.trim().isEmpty() : "Income source cannot be null or empty";
        this.source = source;
    }

    public void setAmount(double amount) {
        assert amount > 0 : "Income amount must be greater than zero";
        this.amount = amount;
    }

    @Override
    public String toString() {
        return source + " - $" + amount;
    }
}
