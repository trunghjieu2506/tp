package budgetsaving.saving;

import budgetsaving.budget.Budget;
import budgetsaving.saving.exceptions.SavingRuntimeException;
import cashflow.model.interfaces.Finance;
import cashflow.model.interfaces.SavingDataManager;
import cashflow.model.interfaces.SavingManager;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import cashflow.model.storage.Storage;
import utils.io.IOHandler;
import utils.money.Money;

public class SavingList implements SavingManager, SavingDataManager {
    private ArrayList<Saving> savings;
    private String currency;
    private Storage savingStorage;

    public SavingList(){
        savings = new ArrayList<>();
    }

    public SavingList(String currency){
        this.currency = currency;
        savings = new ArrayList<>();
    }

    public SavingList(String currency, Storage savingStorage) throws FileNotFoundException {
        this.currency = currency;
        this.savingStorage = savingStorage;
        savings = new ArrayList<>();
        ArrayList<Finance> loadedFile = savingStorage.loadFile();
        if (loadedFile != null){
            for (Finance f : loadedFile) {
                if (f instanceof Saving) {
                    savings.add((Saving) f);
                }
            }
        }
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
    public String setNewSaving(String name, Money amount, LocalDate deadline) throws SavingRuntimeException{
        try {
            Saving goal = new Saving(name, amount, deadline);
            savings.add(goal);
            savingStorage.saveFile(new ArrayList<>(savings));
            return "Saving set: " + goal;
        } catch (SavingRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new SavingRuntimeException(e.getMessage());
        }
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
        savingStorage.saveFile(new ArrayList<>(savings));
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
        for (int i = 0; i < savings.size(); i++) {
            sb.append("Saving " + i + 1 + ". ");
            Saving saving = savings.get(i);
            sb.append(saving.toString());
        }
        return sb.toString();
    }

    @Override
    public void checkSaving(int index) throws SavingRuntimeException{
        Saving saving = savings.get(index);
        if (saving == null){
            throw new SavingRuntimeException("No saving goal found.");
        }
        IOHandler.writeOutput(saving.toStringWithContributions());
    }

    @Override
    public void deleteSaving(int index) throws SavingRuntimeException {
        if (index < 0 || index >= savings.size()) {
            throw new SavingRuntimeException("The index you put is invalid or out of range.");
        }
        try {
            Saving saving = savings.get(index);
            savings.remove(saving);
            savingStorage.saveFile(new ArrayList<>(savings));
            IOHandler.writeOutput("Saving deleted.");
        } catch (Exception e) {
            throw new SavingRuntimeException("Error occured when removing the saving.");
        }
    }

    @Override
    public void deleteContribution(int savingIndex, int contributionIndex) throws SavingRuntimeException{
        if (savingIndex < 0 || savingIndex >= savings.size()) {
            throw new SavingRuntimeException("The index for saving is invalid or out of range.");
        }
        Saving saving = savings.get(savingIndex);
        int cSize = saving.getContributionCount();
        if (contributionIndex < 0 || contributionIndex >= cSize) {
            throw new SavingRuntimeException("The index for contribution is invalid or out of range.");
        }
        SavingContribution cont = saving.getContribution(contributionIndex);
        saving.removeContribution(cont);
        savingStorage.saveFile(new ArrayList<>(savings));
        IOHandler.writeOutput("Contribution deleted.");
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

    @Override
    public ArrayList<Finance> getSavingList() {
        return new ArrayList<>(savings);
    }
}
