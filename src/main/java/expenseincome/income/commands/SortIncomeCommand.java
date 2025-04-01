package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class SortIncomeCommand extends IncomeCommand {
    private boolean mostRecentFirst;

    public SortIncomeCommand(boolean mostRecentFirst) {
        this.mostRecentFirst = mostRecentFirst;
    }

    @Override
    public void execute(IncomeManager manager) {
        manager.sortIncomesByDate(mostRecentFirst);
    }
}
