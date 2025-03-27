package expenseincome.expense.commands;

import expenseincome.expense.ExpenseManager;
import expenseincome.income.IncomeManager;

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
