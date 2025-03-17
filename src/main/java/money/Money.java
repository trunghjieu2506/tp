package money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money implements Comparable<Money> {
    protected Currency currency;
    protected BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Money(CommonCurrencies currency, double amount) {
        this.currency = Currency.getInstance(currency.toString());
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Money(String currency, double amount) throws IllegalArgumentException {
        this.currency = Currency.getInstance(currency);
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString(){
        return currency.getCurrencyCode() + ' ' + amount.toString();
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCurrency(CommonCurrencies currency) {
        this.currency = Currency.getInstance(currency.toString());
    }

    public void setCurrency(String currency) {
        this.currency = Currency.getInstance(currency);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void increment(BigDecimal amount) {
        this.amount = this.amount.add(amount.setScale(2, RoundingMode.HALF_UP));
    }

    public void increment(double percentage) {
        amount = amount.multiply(BigDecimal.valueOf(1.0 + percentage / 100.00)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int compareTo(Money money) throws CurrencyNotMatchException {
        if (this.currency == money.currency) {
            return this.amount.compareTo(money.amount);
        } else {
            throw new CurrencyNotMatchException("Cannot compare as the currencies do not match");
        }
    }
}
