package cashflow.dummy;

import cashflow.model.interfaces.SavingsManager;

public class DummySavings implements SavingsManager {
    public String getSavingsSummary() {
        return "Savings: $" + 0 + " / $" + 0 + "\n\n";
    }
    @Override
    public double getCurrentSavings() {
        return 0;
    }

    @Override
    public void setCurrentSavings(double amount) {

    }

    @Override
    public double getSavingsGoal() {
        return 0;
    }

    @Override
    public void setSavingsGoal(double amount) {

    }

    @Override
    public String getCurrency() {
        return "";
    }

    @Override
    public void setCurrency(String currency) {

    }
}
