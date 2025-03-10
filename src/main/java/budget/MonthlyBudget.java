package budget;

/**
 * Concrete class for handling monthly budget.
 */
public class MonthlyBudget implements IBudget {

    private static final String MSG_BUDGET_SET = "Monthly budget set to $";
    private static final String MSG_CHECK_BUDGET = "You have $%s left for this month, and your budget is $%s.";
    private static final String MSG_INSUFFICIENT_FUNDS = "Insufficient funds for this expense.";

    private double budget;
    private double remaining;

    public MonthlyBudget() {
        this.budget = 0;
        this.remaining = 0;
    }

    // Updated setBudget method:
    @Override
    public void setBudget(double amount) {
        try {
            this.budget = amount;
            this.remaining = amount;
            System.out.println(MSG_BUDGET_SET + amount);
        } catch(Exception e) {
            System.err.println("Error setting budget: " + e.getMessage());
        }
    }

    // Updated checkBudget method:
    @Override
    public void checkBudget() {
        try {
            System.out.println(String.format(MSG_CHECK_BUDGET, remaining, budget));
        } catch(Exception e) {
            System.err.println("Error checking budget: " + e.getMessage());
        }
    }

    // Updated deduct method:
    public void deduct(double amount) {
        try {
            if(amount <= remaining) {
                remaining -= amount;
            } else {
                System.out.println(MSG_INSUFFICIENT_FUNDS);
            }
        } catch(Exception e) {
            System.err.println("Error deducting expense: " + e.getMessage());
        }
    }
}

