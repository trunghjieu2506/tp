package loanbook.commands;

import loanbook.LoanList;
import loanbook.loan.SimpleBulletLoan;
import money.Money;
import people.Person;

public class AddSimpleBulletLoanCommand extends LoanCommand{
    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;

    public AddSimpleBulletLoanCommand(LoanList loans, String description, Person lender, Person borrower, Money money) {
        super(loans);
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    @Override
    public void execute() {
        loans.add(new SimpleBulletLoan(description, lender, borrower, money));
    }
}
