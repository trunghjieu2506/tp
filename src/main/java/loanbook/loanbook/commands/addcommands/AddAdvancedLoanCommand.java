package loanbook.loanbook.commands.addcommands;

import loanbook.loanbook.LoanList;
import loanbook.loanbook.interest.Interest;
import loanbook.loanbook.loan.AdvancedLoan;
import loanbook.loanbook.loan.Loan;
import utils.money.Money;
import utils.people.Person;

import java.time.LocalDate;

public class AddAdvancedLoanCommand extends AddSimpleBulletLoanCommand {
    protected LocalDate startDate;
    protected Interest interest;

    public AddAdvancedLoanCommand(LoanList loans, Person lender, Person borrower,
                                  Money money, LocalDate startDate, Interest interest) {
        super(loans, lender, borrower, money);
        this.startDate = startDate;
        this.interest = interest;
    }

    public AddAdvancedLoanCommand(LoanList loans, String description, Person lender, Person borrower,
                                  Money money, LocalDate startDate, Interest interest) {
        super(loans, description, lender, borrower, money);
        this.startDate = startDate;
        this.interest = interest;
    }

    @Override
    public void execute() {
        Loan loan = new AdvancedLoan(description, lender, borrower, money, startDate, interest);
        loans.add(loan);
        System.out.println("Advanced Loan added: " + loan.basicInfo());
    }
}
