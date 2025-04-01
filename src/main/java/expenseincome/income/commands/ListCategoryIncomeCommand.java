package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class ListCategoryIncomeCommand extends IncomeCommand {
    private String category;

    public ListCategoryIncomeCommand(String category) {
        this.category = category;
    }

    @Override
    public void execute(IncomeManager incomeManager) {
        assert incomeManager != null : "IncomeManager cannot be null.";
        incomeManager.listIncomesByCategory(category);
    }
}
