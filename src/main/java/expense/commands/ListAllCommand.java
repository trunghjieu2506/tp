package expense.commands;

import expense.ExpenseManager;
import income.IncomeManager;

public class ListAllCommand extends ExpenseCommand {
    private IncomeManager incomeManager;

    public ListAllCommand(IncomeManager incomeManager) {
        this.incomeManager = incomeManager;
    }

    @Override
    public void execute(ExpenseManager expenseManager) {
        expenseManager.listExpenses();
        incomeManager.listIncomes();
    }
}
