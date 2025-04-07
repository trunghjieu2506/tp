package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import loanbook.loan.Loan;
import utils.contacts.Person;

import java.util.ArrayList;

public class FindAssociatedLoanCommand extends FindLoanCommand {
    protected Person person;

    public FindAssociatedLoanCommand(LoanManager loanManager, String name) {
        super(loanManager);
        this.person = loanManager.getContactsList().findName(name);
    }

    public FindAssociatedLoanCommand(LoanManager loanManager, Person person) {
        super(loanManager);
        this.person = person;
    }

    @Override
    public void execute() {
        if (person == null) {
            System.out.println("Person not found");
        } else {
            ArrayList<Loan> found = loanManager.findAssociatedLoan(person);
            if (found.isEmpty()) {
                System.out.println("No results found");
            } else {
                System.out.println("Associated loan" + (found.size() == 1 ? "" : "s") + " for [" + person.getName() +
                        (found.size() == 1 ? "] is:" : "] are:"));
                System.out.println(LoanUI.forPrint(found));
            }
        }
    }
}
