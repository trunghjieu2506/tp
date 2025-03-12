package cashflow.model.interfaces;

public interface LoanDebtManager {
    void addDebtOwed(String description, double amount);
    void addDebtOwing(String description, double amount);
//    List<Debt> getDebtsOwed();
//    List<Debt> getDebtsOwing();
    double getTotalDebtOwed();
    double getTotalDebtOwing();
    String getCurrency();
    void setCurrency(String currency);
}
