//package cashflow.dummy;
//
//import cashflow.model.interfaces.ExpenseIncomeManager;
//
//import java.util.Map;
//
//public class DummyExpense implements ExpenseIncomeManager {
//
//    // Hardcoded summary for demonstration.
//    public String getMonthlyExpenseSummary() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Expenses (Month-to-Date):\n");
//        sb.append(" - Food: $120\n");
//        sb.append(" - Transport: $50\n");
//        sb.append(" - Bills: $100\n");
//        sb.append(" - Total: $270\n\n");
//        return sb.toString();
//    }
//
//    @Override
//    public void addExpense(String category, double amount) {
//
//    }
//
//    @Override
//    public void addIncome(String source, double amount) {
//
//    }
//
//    @Override
//    public Map<String, Double> getExpenses() {
//        return Map.of();
//    }
//
//    @Override
//    public double getTotalExpenses() {
//        return 0;
//    }
//
//    @Override
//    public double getTotalIncome() {
//        return 0;
//    }
//
//    @Override
//    public String getCurrency() {
//        return "";
//    }
//
//    @Override
//    public void setCurrency(String currency) {
//
//    }
//}
