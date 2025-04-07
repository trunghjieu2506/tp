package cashflow.stub;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.Finance;
import cashflow.model.storage.Storage;
import expenseincome.expense.ExpenseManager;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StubExpenseManager extends ExpenseManager {

    public StubExpenseManager(FinanceData data, String currency, Storage expenseStorage) throws FileNotFoundException {
        super(data, currency, expenseStorage);
    }

    @Override
    public ArrayList<Finance> getExpenseList() {
        // Return a fixed list with dummy data
        ArrayList<Finance> list = new ArrayList<>();

        // Create an "expense" item for April 10, 2025, $100
        list.add(new Finance() {
            @Override
            public String getType() {
                return "expense";
            }

            @Override
            public double getAmount() {
                return 100.0;
            }

            @Override
            public LocalDate getDate() {
                return LocalDate.of(2025, 4, 10);
            }
        });

        return list;
    }

    @Override
    public String getTopCategory() {
        return "Food (USD100)";
    }
}