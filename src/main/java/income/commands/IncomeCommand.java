package income.commands;

import income.IncomeManager;

public abstract class IncomeCommand {
    public abstract void execute(IncomeManager manager);
}
