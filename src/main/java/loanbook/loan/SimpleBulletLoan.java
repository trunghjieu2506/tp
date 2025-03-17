package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;

/**
 * The loan type that contains only the basic information.
 */
public class SimpleBulletLoan extends Loan {
    public SimpleBulletLoan(String description, Person lender, Person borrower, Money money) {
        super(description, lender, borrower, money);
    }

    public SimpleBulletLoan(String description, Person lender, Person borrower, Money money, LocalDate returnDate) {
        super(description, lender, borrower, money, returnDate);
    }

    @Override
    public String forSave() {
        return "S" + super.forSave();
    }
}
