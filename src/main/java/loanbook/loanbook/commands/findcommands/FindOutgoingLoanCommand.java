package loanbook.loanbook.commands.findcommands;

import loanbook.loanbook.LoanList;
import loanbook.loanbook.commands.LoanCommand;
import utils.people.PeopleList;
import utils.people.Person;

public class FindOutgoingLoanCommand extends LoanCommand {
    protected LoanList loanList;
    protected Person lender;

    public FindOutgoingLoanCommand(LoanList loanList, String name) {
        this.loanList = loanList;
        this.lender = PeopleList.findName(name);
    }

    @Override
    public void execute() {
        if (lender == null) {
            System.out.println("Person not found");
        } else {
            System.out.println("Outgoing loans for " + lender.getName() + " are:");
            System.out.println(loanList.forPrint(loanList.findOutgoingLoan(lender)));
        }

    }
}
