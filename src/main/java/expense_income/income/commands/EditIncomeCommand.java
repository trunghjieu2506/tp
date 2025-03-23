package expense_income.income.commands;

import expense_income.income.IncomeManager;
import java.time.LocalDate;

public class EditIncomeCommand extends IncomeCommand {
    private int index;
    private String newSource;
    private double newAmount;
    private LocalDate newDate;

    public EditIncomeCommand(int index, String newSource, double newAmount, LocalDate newDate) {
        this.index = index;
        this.newSource = newSource;
        this.newAmount = newAmount;
        this.newDate = newDate;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.editIncome(index, newSource, newAmount, newDate);
    }
}
