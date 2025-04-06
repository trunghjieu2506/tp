package budgetsaving.saving;

import budgetsaving.saving.exceptions.SavingRuntimeException;
import cashflow.model.interfaces.SavingManager;
import java.time.LocalDate;
import java.util.ArrayList;

import utils.io.IOHandler;
import utils.money.Money;

public class SavingList implements SavingManager {
    private ArrayList<Saving> savings;
    private String currency;

    public SavingList(){
        savings = new ArrayList<>();
    }

    public SavingList(String currency) {
        this.currency = currency;
        savings = new ArrayList<>();
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
        savings.add(goal);
        return String.format("You have set a new saving goal with \nName: %s\nAmount: %s\nBy: %s",
                name, amount, deadline);
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
    public String listSavings() {
        if (savings.isEmpty()) {
            return "No savings goals set.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= savings.size(); i++) {
            sb.append("Saving " + i + ". ");
            Saving saving = savings.get(i);
            sb.append(saving.toString());
        }
        return sb.toString();
    }

    @Override
    public void checkSaving(int index) {
        Saving saving = savings.get(index);
        if (saving == null){
            System.err.println("No saving goal found.");
            return;
        }
        System.out.println(saving.toStringWithContributions());
    }

    @Override
    public void deleteSaving(int index) throws SavingRuntimeException {
        if (index < 0 || index >= savings.size()) {
            throw new SavingRuntimeException("The index you put is invalid or out of range.");
        }
        try {
            Saving saving = savings.get(index);
            savings.remove(saving);
            IOHandler.writeOutput("Saving deleted.");
        } catch (Exception e) {
            throw new SavingRuntimeException("Error occured when removing the saving.");
        }
    }

    @Override
    public void deleteContribution(int SavingIndex, int ContributionIndex) {

    }

    @Override
    public void modifySaving(int index, Money amount, LocalDate deadline) {
        if (index < 0 || index > savings.size()) {
            System.err.println("Index out of bounds.");
            return;
        }
        Saving saving = savings.get(index);
        if (saving == null){
            System.err.println("No saving goal found.");
            return;
        }
        if (amount != null) {
            saving.setNewAmount(amount);
        }
        if (deadline != null) {
            saving.setNewDeadline(deadline);
        }
    }

    @Override
    public String getSavingsSummary() {
        return "";
    }

}
