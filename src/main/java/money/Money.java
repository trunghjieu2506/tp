package money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Money {
    protected Currency currency;
    protected BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(CommonCurrencies currency, BigDecimal amount) {
        this.currency = Currency.getInstance(currency.toString());
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(String currency, BigDecimal amount) throws IllegalArgumentException {
        this.currency = Currency.getInstance(currency);
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public String toString(){
        return currency.getDisplayName() + ' ' + amount.toString();
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void increment(double percentage) {
        amount = amount.add(amount.multiply(new BigDecimal(percentage / 100))).setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isMoreThan(Money money) throws CurrencyNotMatchException {
        if (this.currency == money.currency) {
            return this.amount.compareTo(money.amount) > 0;
        } else {
            throw new CurrencyNotMatchException("Cannot compare as the currencies do not match");
        }
    }

    public boolean isLessThan(Money money) throws CurrencyNotMatchException {
        if (this.currency == money.currency) {
            return this.amount.compareTo(money.amount) < 0;
        } else {
            throw new CurrencyNotMatchException("Cannot compare as the currencies do not match");
        }
    }

    public boolean equals(Money money) throws CurrencyNotMatchException {
        if (this.currency == money.currency) {
            return this.amount.equals(money.amount);
        } else {
            throw new CurrencyNotMatchException("Cannot compare as the currencies do not match");
        }
    }
}
