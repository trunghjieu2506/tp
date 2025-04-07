package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import utils.contacts.Person;


public class FindOutgoingLoanCommand extends FindLoanCommand {
    protected Person lender;

    public FindOutgoingLoanCommand(LoanManager loanManager, String name) {
        super(loanManager);
        this.lender = loanManager.getContactsList().findName(name);
    }

    public FindOutgoingLoanCommand(LoanManager loanManager, Person person) {
        super(loanManager);
        this.lender = person;
    }

    @Override
    public void execute() {
        if (lender == null) {
            System.out.println("Person not found");
        } else {
            found = loanManager.findOutgoingLoan(lender);
            if (found.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println("Outgoing loan" + (found.size() == 1 ? "" : "s") + " for [" + lender.getName() +
                        (found.size() == 1 ? "] is:" : "] are:"));
                System.out.println(LoanUI.forPrint(found));
            }
        }
    }
}
