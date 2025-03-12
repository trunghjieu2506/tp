package cashflow.model.interfaces;

public interface SavingsManager {
    double getCurrentSavings();
    void setCurrentSavings(double amount);
    double getSavingsGoal();
    void setSavingsGoal(double amount);
    String getCurrency();
    void setCurrency(String currency);
}