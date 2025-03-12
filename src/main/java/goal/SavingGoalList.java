package goal;

import java.util.ArrayList;
import java.util.List;

public class SavingGoalList {

    private static final String ADD_GOAL_FAIL_MESSAGE = "Cannot add a null saving goal.";
    private static final String GOAL_NOT_FOUND_MESSAGE = "Saving goal not found in the list.";
    private static final String GOAL_OUT_OF_BOUND = "Saving goal index out of range.";
    private static final String EMPTY_GOAL_LIST = "No saving goals available.";

    private List<SavingGoal> savingGoals;

    public SavingGoalList() {
        savingGoals = new ArrayList<>();
    }

    public void addGoal(SavingGoal goal) throws SavingGoalException {
        if (goal == null) {
            throw new SavingGoalException(ADD_GOAL_FAIL_MESSAGE);
        }
        savingGoals.add(goal);
    }

    public void removeGoal(SavingGoal goal) throws SavingGoalException {
        if (!savingGoals.contains(goal)) {
            throw new SavingGoalException(GOAL_NOT_FOUND_MESSAGE);
        }
        savingGoals.remove(goal);
    }

    public SavingGoal getGoal(int index) throws SavingGoalException {
        if (index < 0 || index >= savingGoals.size()) {
            throw new SavingGoalException(GOAL_OUT_OF_BOUND);
        }
        return savingGoals.get(index);
    }

    public List<SavingGoal> getAllGoals() {
        return savingGoals;
    }

    public void printAllGoals() {
        if (savingGoals.isEmpty()) {
            System.out.println(EMPTY_GOAL_LIST);
            return;
        }
        for (int i = 0; i < savingGoals.size(); i++) {
            System.out.println("Saving Goal " + (i + 1) + ": " + savingGoals.get(i).toString());
        }
    }
}
