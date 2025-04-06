package cashflow.model.interfaces;

import expenseincome.expense.exceptions.ExpenseException;

import java.util.ArrayList;

public interface ExpenseDataManager {
    public ArrayList<Finance> getExpenseList();
    public String getTopCategory();
}
