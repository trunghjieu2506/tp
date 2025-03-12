package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;

/**
 * The loan type that contains only the basic information.
 */
public class SimpleLoan extends Loan {
    public SimpleLoan(String description, Person lender, Person borrower, Money money) {
        super(description, lender, borrower, money);
    }

    public SimpleLoan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        super(description, lender, borrower, money, deadline);
    }
}
