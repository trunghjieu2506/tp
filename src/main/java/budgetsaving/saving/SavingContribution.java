package budgetsaving.saving;

import utils.money.Money;

import java.time.LocalDate;

public class SavingContribution {
    private Money amount;
    private LocalDate date;

    public SavingContribution(Money amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String toString() {
        return "[ Amount: " + amount.toString() + ", Date=" + date + " ]";
    }
}
