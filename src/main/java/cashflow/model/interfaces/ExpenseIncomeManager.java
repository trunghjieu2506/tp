package cashflow.model.interfaces;

import java.util.Map;

public interface ExpenseIncomeManager {
    void addExpense(String category, double amount);
    void addIncome(String source, double amount);
    Map<String, Double> getExpenses();
    double getTotalExpenses();
    double getTotalIncome();
    String getCurrency();
    void setCurrency(String currency);
    //    void setDefaultCategories(java.util.List<String> defaultCategories);
}
