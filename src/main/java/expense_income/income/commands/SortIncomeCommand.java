package expense_income.income.commands;

import expense_income.income.IncomeManager;

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
