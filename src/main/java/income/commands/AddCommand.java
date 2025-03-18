package income.commands;

import income.IncomeManager;

public class AddCommand extends IncomeCommand {
    private String source;
    private double amount;

    public AddCommand(String source, double amount) {
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void execute(IncomeManager manager) {
        manager.addIncome(source, amount);
    }
}
