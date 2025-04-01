package loanbook.commands.addcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;
import loanbook.loan.SimpleBulletLoan;
import utils.money.Money;
import utils.contacts.Person;

import java.time.LocalDate;

public class AddSimpleBulletLoanCommand extends LoanCommand {
    protected LoanManager loans;
    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;
    protected LocalDate startDate;
    protected LocalDate returnDate;

    public AddSimpleBulletLoanCommand(LoanManager loans, Person lender, Person borrower, Money money) {
        this.loans = loans;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    public AddSimpleBulletLoanCommand(LoanManager loans, String description, Person lender, Person borrower,
                                      Money money) {
        this.loans = loans;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    public AddSimpleBulletLoanCommand(LoanManager loans, String description, Person lender, Person borrower,
                                      Money money, LocalDate returnDate) {
        this.loans = loans;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.returnDate = returnDate;
    }

    public AddSimpleBulletLoanCommand(LoanManager loans, String description, Person lender, Person borrower,
                                      Money money, LocalDate startDate, LocalDate returnDate) {
        this.loans = loans;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }

    @Override
    public void execute() {
        Loan loan = new SimpleBulletLoan(description, lender, borrower, money, startDate, returnDate);
        loans.add(loan);
        System.out.println("Simple Bullet Loan added: " + loan.basicInfo());
    }
}
