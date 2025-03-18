package expense_income.income.commands;

import expense_income.income.IncomeManager;

public abstract class IncomeCommand {
    public abstract void execute(IncomeManager manager);
}
