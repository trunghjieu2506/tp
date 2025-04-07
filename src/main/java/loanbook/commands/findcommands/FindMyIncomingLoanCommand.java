package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.loan.Loan;
import loanbook.ui.LoanUI;

import java.util.ArrayList;

public class FindMyIncomingLoanCommand extends FindIncomingLoanCommand {
    public FindMyIncomingLoanCommand(LoanManager loanManager) {
        super(loanManager, loanManager.getUsername());
    }

    @Override
    public void execute() {
        found = loanManager.findIncomingLoan(borrower);
        if (found.isEmpty()) {
            System.out.println("No results found");
        } else {
            System.out.println("Your incoming loan" + (found.size() == 1 ? " is:" : "s are:"));
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
