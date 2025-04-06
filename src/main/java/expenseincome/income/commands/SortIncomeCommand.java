package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Represents a command to sort all income entries by their dates.
 * Can be sorted either by most recent first or oldest first.
 */
public class SortIncomeCommand extends IncomeCommand {
    private final boolean mostRecentFirst;
    /**
     * Constructs a SortIncomeCommand with a direction flag.
     *
     * @param mostRecentFirst True for sorting by newest first, false for oldest first.
     */
    public SortIncomeCommand(boolean mostRecentFirst) {
        this.mostRecentFirst = mostRecentFirst;
    }
    /**
     * Executes the sort command by sorting incomes by date.
     *
     * @param manager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager manager) {
        manager.sortIncomesByDate(mostRecentFirst);
    }
}
