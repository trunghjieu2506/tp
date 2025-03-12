package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;
import java.time.Period;

/**
 * Advanced loan type containing interest-related information.
 */
public class AdvancedLoan extends Loan {
    protected Money latestMoney;
    protected Period period;
    protected double interest;
    protected int incrementCount;

    public AdvancedLoan(String description, Person lender, Person borrower, Money money, Period period, double interest) {
        super(description, lender, borrower, money);
        this.period = period;
        this.interest = interest;
        latestMoney = money;
        incrementCount = 0;
    }

    public AdvancedLoan(String description, Person lender, Person borrower, Money money, LocalDate deadline, Period period, double interest) {
        super(description, lender, borrower, money, deadline);
        this.period = period;
        this.interest = interest;
        latestMoney = money;
        incrementCount = 0;
    }

    public void increment() {
        latestMoney.increment(interest);
        incrementCount++;
    }

    public Money getLatestMoney() {
        return latestMoney;
    }

    public Period getPeriod() {
        return period;
    }

    public double getInterest() {
        return interest;
    }

    public int getIncrementTimes() {
        return incrementCount;
    }
}
