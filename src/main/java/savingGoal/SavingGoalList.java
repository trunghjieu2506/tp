package savingGoal;

import java.util.ArrayList;
import java.util.List;

public class SavingGoalList {
    private List<SavingGoal> savingGoals;

    public SavingGoalList() {
        savingGoals = new ArrayList<>();
    }

    public void addGoal(SavingGoal goal) throws SavingGoalException {
        if (goal == null) {
            throw new SavingGoalException("Cannot add a null saving goal.");
        }
        savingGoals.add(goal);
    }

    public void removeGoal(SavingGoal goal) throws SavingGoalException {
        if (!savingGoals.contains(goal)) {
            throw new SavingGoalException("Saving goal not found in the list.");
        }
        savingGoals.remove(goal);
    }

    public SavingGoal getGoal(int index) throws SavingGoalException {
        if (index < 0 || index >= savingGoals.size()) {
            throw new SavingGoalException("Saving goal index out of range.");
        }
        return savingGoals.get(index);
    }

    public List<SavingGoal> getAllGoals() {
        return savingGoals;
    }

    public void printAllGoals() {
        if (savingGoals.isEmpty()) {
            System.out.println("No saving goals available.");
            return;
        }
        for (int i = 0; i < savingGoals.size(); i++) {
            System.out.println("Saving Goal " + (i + 1) + ": " + savingGoals.get(i).toString());
        }
    }
}
