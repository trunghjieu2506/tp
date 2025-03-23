package expense_income.income.commands;

import expense_income.income.IncomeManager;

public class EditIncomeCommand extends IncomeCommand {
    private int index;
    private String newSource;
    private double newAmount;

    public EditIncomeCommand(int index, String newSource, double newAmount) {
        this.index = index;
        this.newSource = newSource;
        this.newAmount = newAmount;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager cannot be null.";
        manager.editIncome(index, newSource, newAmount);
    }
}
