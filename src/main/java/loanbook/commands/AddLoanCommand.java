package loanbook.commands;

import loanbook.LoanList;
import money.Money;
import people.Person;

import java.time.LocalDate;

public class AddLoanCommand extends LoanCommand{
    LoanList loans;

    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;
    protected LocalDate deadline;

    public AddLoanCommand(String description, Person lender, Person borrower, Money money) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    public AddLoanCommand(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.deadline = deadline;
    }

    @Override
    public void execute() {
        if (deadline == null) {
            loans.add(description, lender, borrower, money, deadline);
        } else {
            loans.add(description, lender, borrower, money);
        }
    }
}
