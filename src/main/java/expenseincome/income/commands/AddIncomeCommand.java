package expenseincome.income.commands;

import expenseincome.income.IncomeManager;
import java.time.LocalDate;
/**
 * Represents a command to add a new income entry to the income manager.
 * Stores details such as source, amount, date, and category of the income.
 */
public class AddIncomeCommand extends IncomeCommand {
    private final String source;
    private final double amount;
    private final LocalDate date;
    private final String category;
    /**
     * Constructs an AddIncomeCommand with the given income details.
     *
     * @param source   The source of income (e.g., Salary).
     * @param amount   The amount received.
     * @param date     The date the income was received.
     * @param category The category the income belongs to.
     */
    public AddIncomeCommand(String source, double amount, LocalDate date, String category) {
        assert source != null && !source.trim().isEmpty() : "Income description cannot be empty.";
        assert amount > 0 : "Income amount must be greater than zero.";
        assert date != null : "Income date cannot be null.";
        assert category != null : "Income category cannot be null.";

        this.source = source;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
    /**
     * Executes the add income command by adding a new income to the manager.
     *
     * @param manager The income manager handling the logic.
     */
    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance cannot be null.";

        manager.addIncome(source, amount, date, category);
    }
}
