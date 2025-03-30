package loanbook.commands.addcommands;

import loanbook.LoanList;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedLoan;
import loanbook.loan.Loan;
import utils.money.Money;
import utils.people.Person;

import java.time.LocalDate;

public class AddAdvancedLoanCommand extends AddSimpleBulletLoanCommand {
    protected Interest interest;

    public AddAdvancedLoanCommand(LoanList loans, String description, Person lender, Person borrower,
                                  Money money, LocalDate startDate, Interest interest) {
        super(loans, description, lender, borrower, money, startDate, null);
        this.interest = interest;
    }

    public AddAdvancedLoanCommand(LoanList loans, String description, Person lender, Person borrower,
                                  Money money, LocalDate startDate, LocalDate returnDate, Interest interest) {
        super(loans, description, lender, borrower, money, startDate, returnDate);
        this.interest = interest;
    }

    @Override
    public void execute() {
        Loan loan = new AdvancedLoan(description, lender, borrower, money, startDate, returnDate, interest);
        loans.add(loan);
        System.out.println("Advanced Loan added: " + loan.basicInfo());
    }
}
