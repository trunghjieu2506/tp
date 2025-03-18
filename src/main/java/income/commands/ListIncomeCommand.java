package income.commands;

import income.IncomeManager;

public class ListIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager incomeManager) {
        incomeManager.listIncomes();
    }
}
