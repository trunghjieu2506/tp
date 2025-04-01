package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;
import utils.contacts.Person;

import java.util.ArrayList;

public class FindOutgoingLoanCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected Person lender;

    public FindOutgoingLoanCommand(LoanManager loanManager, String name) {
        this.loanManager = loanManager;
        this.lender = loanManager.getContactsList().findName(name);
    }

    public FindOutgoingLoanCommand(LoanManager loanManager, Person person) {
        this.loanManager = loanManager;
        this.lender = person;
    }

    @Override
    public void execute() {
        if (lender == null) {
            System.out.println("Person not found");
        } else {
            ArrayList<Loan> found = loanManager.findOutgoingLoan(lender);
            if (found.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println("Outgoing loans for [" + lender.getName() +
                        (found.size() == 1 ? "] is:" : "] are:"));
                System.out.println(LoanManager.forPrint(found));
            }
        }
    }
}
