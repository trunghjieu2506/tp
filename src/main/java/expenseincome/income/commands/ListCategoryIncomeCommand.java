package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Represents a command to list all income entries that belong to a specific category.
 */
public class ListCategoryIncomeCommand extends IncomeCommand {
    private final String category;
    /**
     * Constructs the command with a specific category to filter by.
     *
     * @param category The category to list incomes from.
     */
    public ListCategoryIncomeCommand(String category) {
        this.category = category;
    }
    /**
     * Executes the command by listing all incomes in the given category.
     *
     * @param incomeManager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager incomeManager) {
        assert incomeManager != null : "IncomeManager cannot be null.";
        incomeManager.listIncomesByCategory(category);
    }
}
