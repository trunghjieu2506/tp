package expense_income.expense.commands;

import expense_income.expense.ExpenseManager;
import expense_income.income.IncomeManager;

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
