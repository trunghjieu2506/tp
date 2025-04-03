package cashflow.model.interfaces;

import java.util.ArrayList;

public interface ExpenseDataManager {
    ArrayList<Finance> getExpenseList();
    String getTopCategory();
}
