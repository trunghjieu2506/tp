package loanbook.loan;

import loanbook.interest.Interest;
import utils.money.Money;
import utils.contacts.Person;

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

    public AdvancedLoan(String description, Person lender, Person borrower, Money money,
                        LocalDate startDate, Interest interest) throws DateUndefinedException {
        super(description, lender, borrower, money);
        if (startDate == null) {
            throw new DateUndefinedException("Start date not defined");
        }
        this.interest = interest;
        this.startDate = startDate;
        outstandingBalance = money;
        incrementCount = 0;
    }

    public AdvancedLoan(String description, Person lender, Person borrower, Money money,
                        LocalDate startDate, LocalDate returnDate, Interest interest) throws DateUndefinedException {
        super(description, lender, borrower, money, startDate, returnDate);
        if (startDate == null) {
            throw new DateUndefinedException("Start date not defined");
        }
        this.interest = interest;
        outstandingBalance = money;
        incrementCount = 0;
    }

    @Override
    public Money getBalance() {
        calculateBalance();
        return outstandingBalance;
    }

    public int incrementCount() {
        return incrementCount;
    }

    @Override
    public void setStart(LocalDate date) {
        this.startDate = date;
        calculateBalance();
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
        calculateBalance();
    }

    public void calculateBalance() {
        if (startDate == null) {
            throw new DateUndefinedException("Missing start date");
        }
        LocalDate date = startDate;
        Money balance = new Money(principal.getCurrency(), principal.getAmount());
        incrementCount = 0;
        while (date.plus(interest.period()).isBefore(LocalDate.now())) {
            if (interest.type() == SIMPLE) {
                BigDecimal increment = this.principal.getAmount().multiply(BigDecimal.valueOf(interest.rate() / 100));
                balance.increment(increment);
            } else {
                balance.increment(interest.rate());
            }
            incrementCount++;
            date = date.plus(interest.period());
        }
        outstandingBalance = balance;
    }

    /**
     * Displays the basic information of the loan.
     * @return a ready-to-print <code>String</code> containing all necessary information.
     */
    @Override
    public String basicInfo() {
        calculateBalance();
        return "Lender: [" + lender.getName()
                + "]    Borrower: [" + borrower.getName()
                + "]    Amount: " + principal
                + "    Start Date: " + startDate
                + (returnDate == null ? "" : "    Return Date: " + returnDate) + '\n'
                + "    Interest: " + interest + '\n'
                + "    Outstanding Balance: " + outstandingBalance + '\n'
                + (isReturned ? "    Returned" : "    Not Returned");
    }

    /**
     * Shows all details of this loan.
     * @return a ready-to-print <code>String</code> containing all information. Multiple lines.
     */
    @Override
    public String showDetails() {
        calculateBalance();
        return super.showDetails()
                + "Interest: " + interest + '\n'
                + "Outstanding Balance: " + outstandingBalance + '\n'
                + (isReturned ? "Returned" : "Not Returned");
    }

    @Override
    public String forSave() {
        return "<AdvancedLoanStart>\n" +
                super.forSave() +
                "<IsReturned>" + isReturned + '\n' +
                "<Interest>" + interest.forSave() + '\n' +
                "<AdvancedLoanEnd>";
    }
}
