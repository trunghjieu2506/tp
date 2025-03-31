package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;
import utils.contacts.Person;

import java.util.ArrayList;

public class FindIncomingLoanCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected Person borrower;

    public FindIncomingLoanCommand(LoanManager loanManager, String name) {
        this.loanManager = loanManager;
        this.borrower = loanManager.getContactsList().findName(name);
    }

    public FindIncomingLoanCommand(LoanManager loanManager, Person person) {
        this.loanManager = loanManager;
        this.borrower = person;
    }

    @Override
    public void execute() {
        if (borrower == null) {
            System.out.println("Person not found");
        } else {
            ArrayList<Loan> found = loanManager.findIncomingLoan(borrower);
            if (found.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println("Incoming loans for [" + borrower.getName() +
                        (found.size() == 1 ? "] is:" : "] are:"));
                System.out.println(LoanManager.forPrint(found));
            }
        }
    }
}
