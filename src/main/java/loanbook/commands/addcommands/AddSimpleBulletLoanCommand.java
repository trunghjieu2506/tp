package loanbook.commands.addcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;
import loanbook.loan.SimpleBulletLoan;
import utils.money.Money;
import utils.contacts.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddSimpleBulletLoanCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;
    protected LocalDate startDate;
    protected LocalDate returnDate;
    protected ArrayList<String> tags;

    public AddSimpleBulletLoanCommand(LoanManager loanManager, Person lender, Person borrower, Money money) {
        this.loanManager = loanManager;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    public AddSimpleBulletLoanCommand(LoanManager loanManager, String description, Person lender, Person borrower,
                                      Money money) {
        this.loanManager = loanManager;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
    }

    public AddSimpleBulletLoanCommand(LoanManager loanManager, String description, Person lender, Person borrower,
                                      Money money, LocalDate returnDate) {
        this.loanManager = loanManager;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.returnDate = returnDate;
    }

    public AddSimpleBulletLoanCommand(LoanManager loanManager, String description, Person lender, Person borrower,
                                      Money money, LocalDate startDate, LocalDate returnDate) {
        this.loanManager = loanManager;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }

    public AddSimpleBulletLoanCommand(LoanManager loanManager, String description, Person lender, Person borrower,
                                      Money money, LocalDate startDate, LocalDate returnDate, ArrayList<String> tags) {
        this.loanManager = loanManager;
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.tags = tags;
    }

    @Override
    public void execute() {
        Loan loan = new SimpleBulletLoan(description, lender, borrower, money, startDate, returnDate);
        loan.addTags(tags);
        loanManager.addAndStore(loan);
        System.out.println("Simple Bullet Loan added: " + loan.basicInfo());
    }
}
