package loanbook.loan;

import loanbook.interest.Interest;
import money.Money;
import people.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

import static loanbook.interest.InterestType.SIMPLE;

/**
 * Advanced loan type containing interest-related information.
 */
public class AdvancedLoan extends Loan {
    protected Money outstandingBalance;
    protected Interest interest;
    protected int incrementCount;

    public AdvancedLoan(String description, Person lender, Person borrower, Money money, Interest interest) {
        super(description, lender, borrower, money);
        this.interest = interest;
        outstandingBalance = money;
        incrementCount = 0;
    }

    public Money outstandingBalance() {
        return outstandingBalance;
    }

    public int incrementCount() {
        return incrementCount;
    }

    public void applyInterest() {
        if (interest.type() == SIMPLE) {
            BigDecimal increment = this.principal.getAmount().multiply(BigDecimal.valueOf(interest.rate()));
            outstandingBalance.increment(increment);
        } else {
            outstandingBalance.increment(interest.rate());
        }
        incrementCount++;
    }

    public void applyInterest(int count) {
        if (interest.type() == SIMPLE) {
            BigDecimal increment = this.principal.getAmount().multiply(BigDecimal.valueOf(interest.rate()));
            outstandingBalance.increment(increment.multiply(BigDecimal.valueOf(count)));
        } else {
            for (int i = 0; i < count; i++) {
                outstandingBalance.increment(interest.rate());
            }
        }
        incrementCount++;
    }
}
