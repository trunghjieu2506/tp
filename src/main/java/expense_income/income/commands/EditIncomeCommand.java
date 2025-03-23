package expense_income.income.commands;

import expense_income.income.IncomeManager;
import java.time.LocalDate;

public class EditIncomeCommand extends IncomeCommand {
    private int index;
    private String newSource;
    private double newAmount;
    private LocalDate newDate;
    private String newCategory;

    public EditIncomeCommand(int index, String newSource, double newAmount, LocalDate newDate, String newCategory) {
        this.index = index;
        this.newSource = newSource;
        this.newAmount = newAmount;
        this.newDate = newDate;
        this.newCategory = newCategory;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.editIncome(index, newSource, newAmount, newDate, newCategory);
    }
}
