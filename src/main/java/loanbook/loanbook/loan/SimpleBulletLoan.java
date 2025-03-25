package loanbook.loanbook.loan;

import utils.money.Money;
import utils.people.Person;

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

    /**
     * Basic information of this loan.
     * @return a ready-to-print <code>String</code> containing all basic information. Multiple lines.
     */
    @Override
    public String basicInfo() {
        return "Lender: [" + lender.getName()
                + "]    Borrower: [" + borrower.getName()
                + "]    Amount: " + principal + '\n'
                + (isReturned ? "    Returned" : "    Not Returned");
    }

    /**
     * Shows all details of this loan.
     * @return a ready-to-print <code>String</code> containing all information. Multiple lines.
     */
    @Override
    public String showDetails() {
        return super.showDetails()
                + (isReturned ? "Returned" : "Not Returned");
    }
}
