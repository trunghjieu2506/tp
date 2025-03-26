package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public abstract class IncomeCommand {
    public abstract void execute(IncomeManager manager);
}
