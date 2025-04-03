package cashflow.model.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface ExpenseDataManager {
    public ArrayList<Finance> getExpenseList();
    public String getTopCategory();
}
