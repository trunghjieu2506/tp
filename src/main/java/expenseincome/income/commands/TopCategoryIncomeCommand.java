package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
/**
 * Represents a command to find and print the income category with the highest total amount.
 */
public class TopCategoryIncomeCommand extends IncomeCommand {
    /**
     * Executes the command to print the top income category.
     *
     * @param manager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.printTopCategory();
    }
}
