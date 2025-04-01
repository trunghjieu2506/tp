package expenseincome.income.commands;

import expenseincome.income.IncomeManager;

public class ListIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager incomeManager) {
        assert incomeManager != null : "IncomeManager instance cannot be null.";
        incomeManager.listIncomes();
    }
}
