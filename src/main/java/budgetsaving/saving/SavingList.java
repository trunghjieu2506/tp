package budgetsaving.saving;

import cashflow.model.interfaces.SavingManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import utils.money.Money;

public class SavingList implements SavingManager {
    private Map<String, Saving> savings;
    private String currency;

    public SavingList(){
        savings = new HashMap<>();
    }

    public SavingList(String currency) {
        this.currency = currency;
        savings = new HashMap<>();
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Creates a new savings goal.
     * Format: set-goal n/GOAL_NAME a/AMOUNT b/BY
     */
    @Override
    public String setNewSaving(String name, Money amount, LocalDate deadline) {
        Saving goal = new Saving(name, amount, deadline);
        savings.put(name, goal);
        return String.format("You have set a new saving goal with \nName: %s\nAmount: %s\nBy: %s",
                name, amount.toString(), deadline);
    }

    /**
     * Contributes an amount to an existing savings goal.
     * Format: contribute-goal n/GOAL_NAME a/AMOUNT
     */
    @Override
    public String contributeToSaving(String name, Money amount) {
        Saving goal = savings.get(name);
        if (goal == null) {
            return "Savings goal not found.";
        }
        goal.addContribution(amount);
        Money left = new Money(
                goal.getGoalAmount().getCurrency(),
                goal.getGoalAmount().getAmount().subtract(goal.getCurrentAmount().getAmount())
        );
        return String.format("You have funded your saving goal\nName: %s\nAmount: %s\nAmount left to save: %s\nBy: %s",
                name, amount.toString(), left.toString(), goal.getDeadline());
    }

    @Override
    public String contributeToSaving(int index, Money amount) {
        Saving saving =  savings.get(index);
        if (saving == null) {
            return "Savings goal not found.";
        }
        saving.addContribution(amount);
        Money left = new Money(
                saving.getGoalAmount().getCurrency(),
                saving.getGoalAmount().getAmount().subtract(saving.getCurrentAmount().getAmount())
        );
        return "You have funded your saving goal. Good job!\n" + saving;
    }

    /**
     * Checks and displays all savings goals and their progress.
     * Format: check-goal
     */
    @Override
    public String listGoals() {
        if (savings.isEmpty()) {
            return "No savings goals set.";
        }
        StringBuilder sb = new StringBuilder();
        for (Saving goal : savings.values()) {
            sb.append(String.format("Name: %s\nTotal Amount: %s\nSaved: %s\nBy: %s\n\n",
                    goal.getName(), goal.getGoalAmount().toString(),
                    goal.getCurrentAmount().toString(), goal.getDeadline()));
        }
        return sb.toString();
    }

    @Override
    public void checkOneGoal(int index) {
        Saving saving = savings.get(index);
        if (saving == null){
            System.err.println("No saving goal found.");
            return;
        }
        System.out.println(saving.toStringWithContributions());
    }

    @Override
    public void modifySaving(int index, Money amount, LocalDate deadline) {

    }

}
