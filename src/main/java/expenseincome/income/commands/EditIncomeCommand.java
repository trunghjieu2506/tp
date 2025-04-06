package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
import java.time.LocalDate;
/**
 * Represents a command to edit an existing income entry identified by its index.
 * Allows updating of source, amount, date, and category.
 */
public class EditIncomeCommand extends IncomeCommand {
    private final int index;
    private final String newSource;
    private final double newAmount;
    private final LocalDate newDate;
    private final String newCategory;
    /**
     * Constructs an EditIncomeCommand with the specified updates.
     *
     * @param index        The 1-based index of the income to edit.
     * @param newSource    The new source name.
     * @param newAmount    The new amount value.
     * @param newDate      The new income date.
     * @param newCategory  The new category label.
     */
    public EditIncomeCommand(int index, String newSource, double newAmount, LocalDate newDate, String newCategory) {
        this.index = index;
        this.newSource = newSource;
        this.newAmount = newAmount;
        this.newDate = newDate;
        this.newCategory = newCategory;
    }
    /**
     * Executes the edit income command by modifying the income entry at the given index.
     *
     * @param manager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.editIncome(index, newSource, newAmount, newDate, newCategory);
    }
}
