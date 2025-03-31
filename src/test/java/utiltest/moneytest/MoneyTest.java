package utiltest.moneytest;

import utils.money.CommonCurrencies;
import utils.money.CurrencyNotMatchException;
import utils.money.Money;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {
    @Test
    public void moneyPrintTest() {
        Money testOne = new Money(CommonCurrencies.SGD, 21.13);
        Money testTwo = new Money(Currency.getInstance("USD"), 123);
        Money testThree = new Money("SGD", 23.3434);
        assertEquals("SGD 21.13", testOne.toString());
        assertEquals("USD 123.00", testTwo.toString());
        assertEquals("SGD 23.34", testThree.toString());
    }

    @Test
    public void moneyCompareTest() {
        Money testOne = new Money(CommonCurrencies.SGD, 21.13);
        Money testTwo = new Money(Currency.getInstance("USD"), 123);
        Money testThree = new Money("SGD", 23.3434);
        assertEquals(-1, testOne.compareTo(testThree));
        try {
            System.out.println(testOne.compareTo(testTwo));
        } catch (CurrencyNotMatchException e) {
            System.out.println("not the same currency");
        }
    }
}
