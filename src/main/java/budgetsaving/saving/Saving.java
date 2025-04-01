package budgetsaving.saving;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;

import budgetsaving.saving.utils.SavingStatus;
import utils.money.Money;

public class Saving {
    private String name;
    private Money goalAmount;
    private Money currentAmount;
    private LocalDate deadline;
    private SavingStatus status;

    private ArrayList<SavingContribution> contributions;

    public Saving(String name, Money goalAmount, LocalDate deadline) {
        this.name = name;
        this.goalAmount = goalAmount;
        this.deadline = deadline;
        this.status = SavingStatus.ACTIVE;
        this.contributions = new ArrayList<>();
        // Initialize current amount to zero with the same currency as goalAmount.
        this.currentAmount = new Money(goalAmount.getCurrency(), BigDecimal.ZERO);
    }

    public String getName() {
        return name;
    }

    public Money getGoalAmount() {
        return goalAmount;
    }

    public Money getCurrentAmount() {
        return currentAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public SavingStatus getStatus() {
        return status;
    }

    private void updateStatus(SavingStatus status) {
        this.status = status;
        System.out.println("Saving status updated to: " + status);
    }

    private void updateAmount(Money newAmount) {
        //different currency
        if (!newAmount.getCurrency().equals(goalAmount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch: Expected "
                    + goalAmount.getCurrency() + " but got " + newAmount.getCurrency());
        }
        //restrict the saving amount to be 0 < curr < goal
        if (newAmount.getAmount().compareTo(goalAmount.getAmount()) > 0) {
            newAmount = goalAmount;
            updateStatus(SavingStatus.COMPLETED);
        } else if (newAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            newAmount = new Money(goalAmount.getCurrency(), BigDecimal.ZERO);
        }
        currentAmount = newAmount;
    }

    private void addNewContribution(SavingContribution contribution) {
        if (contributions == null) {
            throw new RuntimeException("Saving contributions is null");
        }
        contributions.add(contribution);
    }

    public void addContribution(Money contribution) {
        currentAmount.increment(contribution.getAmount());
        //double check currency and restrict amount
        SavingContribution newContribution = new SavingContribution(contribution, LocalDate.now());
        addNewContribution(newContribution);
        System.out.println("Saving contribution added.\n" + this);
        updateAmount(currentAmount);
    }

    @Override
    public String toString() {
        return "Saving: { name=" + name + ", goalAmount=" + goalAmount
                + ", currentAmount=" + currentAmount + ", deadline=" + deadline + " }";
    }

    public String toStringWithContributions() {
        StringBuilder sb = new StringBuilder();
        sb.append(this + "\n");
        sb.append("Saving contributions: \n");
        for (int i = 0; i < contributions.size(); i++) {
            SavingContribution contribution = contributions.get(i);
            if (contribution == null) {
                throw new RuntimeException("Saving contribution is null");
            }
            sb.append("Contribution " + i + ". " + contributions.get(i).toString());
        }
        return sb.toString();
    }

    public void printCompletionMessage(){
        System.out.println("Congratulations! You have completed the saving goal!");
        System.out.println("Here is a quick look at your saving progress: ");
        System.out.println(toStringWithContributions());
    }

    public void setNewAmount(Money amount) {
        this.goalAmount = amount;
    }

    public void setNewDeadline(LocalDate deadline){
        this.deadline = deadline;
    }
}
