package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class BottomCategoryIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.printBottomCategory();
    }
}
