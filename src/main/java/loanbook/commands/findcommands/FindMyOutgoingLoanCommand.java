package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;


public class FindMyOutgoingLoanCommand extends FindOutgoingLoanCommand {
    public FindMyOutgoingLoanCommand(LoanManager loanManager) {
        super(loanManager, loanManager.getUsername());
    }

    @Override
    public void execute() {
        found = loanManager.findOutgoingLoan(lender);
        if (found.isEmpty()) {
            System.out.println("No results found");
        } else {
            System.out.println("Your outgoing loan" + (found.size() == 1 ? " is:" : "s are:"));
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
