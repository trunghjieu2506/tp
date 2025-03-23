package expense_income.income.commands;

import expense_income.income.IncomeManager;

public class AddIncomeCommand extends IncomeCommand {
    private String source;
    private double amount;

    public AddIncomeCommand(String source, double amount) {
        assert source != null && !source.trim().isEmpty() : "Income description cannot be empty.";
        assert amount > 0 : "Income amount must be greater than zero.";

        this.source = source;
        this.amount = amount;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance cannot be null.";

        manager.addIncome(source, amount);
    }
}
