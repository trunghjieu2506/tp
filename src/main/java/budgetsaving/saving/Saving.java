package budgetsaving.saving;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;

import budgetsaving.saving.exceptions.SavingRuntimeException;
import budgetsaving.saving.utils.SavingStatus;
import cashflow.model.interfaces.Finance;
import utils.io.IOHandler;
import utils.money.Money;
import utils.textcolour.TextColour;

public class Saving extends Finance {
    private static final long serialVersionUID = 1L;
    private String name;
    private Money goalAmount;
    private Money currentAmount;
    private LocalDate deadline;
    private SavingStatus status;

    private ArrayList<SavingContribution> contributions;

    public Saving(String name, Money goalAmount, LocalDate deadline) throws SavingRuntimeException {
        if (name == null || name.trim().isEmpty()) {
            throw new SavingRuntimeException("Saving name cannot be null or empty.");
        }
        if (goalAmount == null) {
            throw new SavingRuntimeException("Total amount cannot be null.");
        }
        if (goalAmount.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new SavingRuntimeException("Total amount cannot be negative.");
        }
        if (goalAmount.getCurrency() == null) {
            throw new SavingRuntimeException("Saving currency cannot be null or empty.");
        }
        if (deadline == null) {
            throw new SavingRuntimeException("End date cannot be null.");
        }
        if (deadline.isBefore(LocalDate.now())) {
            throw new SavingRuntimeException("End date must be in the future.");
        }
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

    public SavingContribution getContribution(int index) {
        return contributions.get(index);
    }
    public int getContributionCount() {
        return contributions.size();
    }

    private void updateStatus(SavingStatus status) {
        this.status = status;
        printCompletionMessage();
    }

    private void updateAmount(Money newAmount) {
        //different currency
        if (!newAmount.getCurrency().equals(goalAmount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch: Expected "
                    + goalAmount.getCurrency() + " but got " + newAmount.getCurrency());
        }
        //restrict the saving amount to be 0 < curr < goal
        if (newAmount.getAmount().compareTo(goalAmount.getAmount()) >= 0) {
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
        if (currentAmount.getAmount().doubleValue() == goalAmount.getAmount().doubleValue()) {
            IOHandler.writeWarning("Saving contribution is already completed.");
            return;
        }
        currentAmount.increment(contribution.getAmount());
        //double check currency and restrict amount
        SavingContribution newContribution = new SavingContribution(contribution, LocalDate.now());
        addNewContribution(newContribution);
        IOHandler.writeOutput("Saving contribution added.");
        updateAmount(currentAmount);
    }

    @Override
    public String toString() {
        return "[" + status + "] " + "{Name: " + name + ", Goal: " + goalAmount
                + ", Current Progress: " + currentAmount + ", By: " + deadline + "}";
    }

    public String toStringWithContributions() {
        StringBuilder sb = new StringBuilder();
        sb.append(this + "\n");
        sb.append("\tSaving contributions: \n");
        if (contributions.isEmpty()){
            sb.append("\tNo contributions found.\n");
            return sb.toString();
        }
        for (int i = 0; i < contributions.size(); i++) {
            SavingContribution contribution = contributions.get(i);
            if (contribution == null) {
                throw new RuntimeException("Saving contribution is null");
            }
            sb.append("\tContribution " + i + 1 + ". " + contributions.get(i).toString() + "\n");
        }
        return sb.toString();
    }

    public void printCompletionMessage(){
        IOHandler.writeOutputWithColour(
                "Congratulations! You have completed the saving goal!", TextColour.GREEN);
    }

    public void setNewAmount(Money amount) {
        this.goalAmount = amount;
    }

    public void setNewDeadline(LocalDate deadline){
        this.deadline = deadline;
    }

    public void removeContribution(SavingContribution cont) throws SavingRuntimeException {
        try{
            Money contributedAmount = cont.getAmount();
            double amount = contributedAmount.getAmount().doubleValue();
            double newAmount = currentAmount.getAmount().doubleValue() - amount;
            contributions.remove(cont);
            currentAmount.setAmount(BigDecimal.valueOf(newAmount));
        } catch (Exception e){
            throw new SavingRuntimeException(e.getMessage());
        }
    }

    @Override
    public LocalDate getDate() {
        return deadline;
    }

    @Override
    public double getAmount() {
        return currentAmount.getAmount().doubleValue();
    }

    @Override
    public String getType() {
        return name;
    }
}
