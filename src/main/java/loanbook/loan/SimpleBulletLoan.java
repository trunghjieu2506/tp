package loanbook.loan;

import utils.money.Money;
import utils.contacts.Person;

import java.time.LocalDate;

/**
 * Simple Bullet Loans do not apply interests. Start and return dates are optional.
 */
public class SimpleBulletLoan extends Loan {
    public SimpleBulletLoan(String description, Person lender, Person borrower, Money money) {
        super(description, lender, borrower, money);
    }

    public SimpleBulletLoan(String description, Person lender, Person borrower, Money money, LocalDate returnDate) {
        super(description, lender, borrower, money, returnDate);
    }

    public SimpleBulletLoan(String description, Person lender, Person borrower, Money money, LocalDate startDate,
                            LocalDate returnDate) {
        super(description, lender, borrower, money, startDate, returnDate);
    }

    /**
     * The balance equals the principal for simple bullet loanManager.
     * @return a <code>Money</code> class representing the current balance.
     */
    @Override
    public Money getBalance() {
        return principal;
    }

    /**
     * A method used to create storage files easily.
     * @return a <code>String</code> that stores every information of this loan and can be read by
     *     <code>LoanSaveManager</code>.
     */
    @Override
    public String forSave() {
        return "<SimpleBulletLoanStart>\n" +
                super.forSave() + '\n' +
                "<IsReturned>" + isReturned + '\n' +
                "<SimpleBulletLoanEnd>";
    }

    /**
     * A method used for the output of the basic information of this loan to the user interface.
     * @return a ready-to-print <code>String</code> containing all basic information. Multiple lines.
     */
    @Override
    public String basicInfo() {
        return "Lender: [" + lender.getName()
                + "]    Borrower: [" + borrower.getName()
                + "]\n    Amount: " + principal
                + (isReturned ? "    Returned" : "    Not Returned");
    }

    /**
     * A method used for the output of all information of this loan to the user interface.
     * @return a ready-to-print <code>String</code> containing all information. Multiple lines.
     */
    @Override
    public String showDetails() {
        return super.showDetails()
                + (isReturned ? "Returned" : "Not Returned");
    }

    @Override
    public String getType()
    {
        return "Overview";
    }
    @Override
    public double getAmount()
    {
        return 0;
    }

    @Override
    public LocalDate getDate(){
        return startDate;
    }
}
