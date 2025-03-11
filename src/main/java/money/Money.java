package money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

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
}
