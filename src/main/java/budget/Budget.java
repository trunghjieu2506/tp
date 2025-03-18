package budget;

import money.Money;
import java.math.BigDecimal;

public class Budget {
    private String name;
    private Money totalBudget;
    private Money remainingBudget;

    public Budget(String name, Money totalBudget) {
        this.name = name;
        this.totalBudget = totalBudget;
        // Initialize remainingBudget with the same currency and amount as totalBudget
        this.remainingBudget = new Money(totalBudget.getCurrency(), totalBudget.getAmount());
    }

    // Getter for budget name
    public String getName() {
        return name;
    }

    public Money getTotalBudget() {
        return totalBudget;
    }

    public Money getRemainingBudget() {
        return remainingBudget;
    }

    // Setters now accept a double and convert it to a BigDecimal internally
    public void setTotalBudget(double amount) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        totalBudget.setAmount(amt);
    }

    public void setRemainingBudget(double amount) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        remainingBudget.setAmount(amt);
    }

    // Deducts an amount from the remaining budget
    public void deduct(double amount) {
        BigDecimal deduction = BigDecimal.valueOf(amount);
        BigDecimal current = remainingBudget.getAmount();
        if (current.compareTo(deduction) < 0) {
            System.out.println("Not enough budget to deduct " + amount);
        } else {
            remainingBudget.setAmount(current.subtract(deduction));
        }
    }

    // Adds an amount to both the total and remaining budget
    public void add(double amount) {
        BigDecimal addition = BigDecimal.valueOf(amount);
        remainingBudget.setAmount(remainingBudget.getAmount().add(addition));
        totalBudget.setAmount(totalBudget.getAmount().add(addition));
    }

    @Override
    public String toString() {
        return "Budget {name='" + name + "', totalBudget=" + totalBudget.toString() +
                ", remainingBudget=" + remainingBudget.toString() + "}";
    }
}
