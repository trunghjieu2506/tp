package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import utils.contacts.Person;

public class FindIncomingLoanCommand extends FindLoanCommand {
    protected Person borrower;

    public FindIncomingLoanCommand(LoanManager loanManager, String name) {
        super(loanManager);
        this.borrower = loanManager.getContactsList().findName(name);
    }

    public FindIncomingLoanCommand(LoanManager loanManager, Person person) {
        super(loanManager);
        this.borrower = person;
    }

    @Override
    public void execute() {
        if (borrower == null) {
            System.out.println("Person not found");
        } else {
            found = loanManager.findIncomingLoan(borrower);
            if (found.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println("Incoming loans for [" + borrower.getName() +
                        (found.size() == 1 ? "] is:" : "] are:"));
                System.out.println(LoanUI.forPrint(found));
            }
        }
    }
}
