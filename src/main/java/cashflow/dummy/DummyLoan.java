package cashflow.dummy;

import cashflow.model.interfaces.LoanDebtManager;

public class DummyLoan implements LoanDebtManager {

    public String getLoanDebtSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Debt (You owe others): $" + 0 + "\n");
        sb.append("Debt (Others owe you): $" + 0 + "\n");
        return sb.toString();
    }

    @Override
    public void addDebtOwed(String description, double amount) {

    }

    @Override
    public void addDebtOwing(String description, double amount) {

    }

    @Override
    public double getTotalDebtOwed() {
        return 0;
    }

    @Override
    public double getTotalDebtOwing() {
        return 0;
    }

    @Override
    public String getCurrency() {
        return "";
    }

    @Override
    public void setCurrency(String currency) {

    }
}
