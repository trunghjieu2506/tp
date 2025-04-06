package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;

import java.util.ArrayList;

public abstract class FindLoanCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected ArrayList<Loan> found;

    public FindLoanCommand(LoanManager loanManager) {
        this.loanManager = loanManager;
    }
}
