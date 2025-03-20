package expense_income.expense;

public class Expense {
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        assert description != null && !description.trim().isEmpty() : "Expense description cannot be empty.";
        assert amount > 0 : "Expense amount must be greater than zero.";

        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return description + " - $" + amount;
    }
}
