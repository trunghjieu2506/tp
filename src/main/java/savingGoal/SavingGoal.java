package savingGoal;

/**
 * Represents a savings goal.
 */
public class SavingGoal {
    private String name;
    private double targetAmount;
    private double contributed;
    private String deadline;

    private static final String MSG_CONTRIBUTION_EXCEEDS = "Contribution exceeds goal amount.";

    public SavingGoal(String name, double targetAmount, String deadline) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.contributed = 0;
    }

    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getContributed() {
        return contributed;
    }

    public String getDeadline() {
        return deadline;
    }

    /**
     * Calculates the remaining amount needed to reach the goal.
     * @return Remaining amount.
     */
    public double getAmountLeft() {
        return targetAmount - contributed;
    }


    // Updated addContribution method:
    public void addContribution(double amount) {
        if(amount > getAmountLeft()) {
            System.out.println(MSG_CONTRIBUTION_EXCEEDS);
        } else {
            contributed += amount;
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Target Amount: $" + targetAmount + "\n" +
                "Contributed: $" + contributed + "\n" +
                "Amount left to save: $" + getAmountLeft() + "\n" +
                "By: " + deadline;
    }
}

