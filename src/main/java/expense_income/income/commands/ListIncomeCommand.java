package expense_income.income.commands;

import expense_income.income.IncomeManager;

public class ListIncomeCommand extends IncomeCommand {
    @Override
    public void execute(IncomeManager incomeManager) {
        incomeManager.listIncomes();
    }
}
