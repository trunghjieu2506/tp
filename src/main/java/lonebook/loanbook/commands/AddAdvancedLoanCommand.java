package lonebook.loanbook.commands;

import lonebook.loanbook.LoanList;
import lonebook.loanbook.interest.Interest;
import lonebook.loanbook.loan.AdvancedLoan;
import utils.money.Money;
import utils.people.Person;

import java.time.LocalDate;

public class AddAdvancedLoanCommand extends AddSimpleBulletLoanCommand{
    protected LocalDate startDate;
    protected Interest interest;

    public AddAdvancedLoanCommand(LoanList loans, String description, Person lender, Person borrower,
                                  Money money, LocalDate startDate, Interest interest) {
        super(loans, description, lender, borrower, money);
        this.startDate = startDate;
        this.interest = interest;
    }

    @Override
    public void execute() {
        loans.add(new AdvancedLoan(description, lender, borrower, money, startDate, interest));
    }
}
