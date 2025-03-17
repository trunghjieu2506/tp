package loanbook.commands;

import loanbook.LoanList;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedLoan;
import money.Money;
import people.Person;

import java.time.LocalDate;
import java.time.Period;

public class AddAdvancedLoanCommand extends AddSimpleBulletLoanCommand{
    protected LocalDate startDate;
    protected Interest interest;

    public AddAdvancedLoanCommand(LoanList loans, String description, Person lender, Person borrower, Money money, LocalDate startDate, Interest interest) {
        super(loans, description, lender, borrower, money);
        this.startDate = startDate;
        this.interest = interest;
    }

    @Override
    public void execute() {
        loans.add(new AdvancedLoan(description, lender, borrower, money, startDate, interest));
    }
}
