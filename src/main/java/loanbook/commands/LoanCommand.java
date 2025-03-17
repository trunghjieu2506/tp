package loanbook.commands;

import loanbook.LoanList;

public abstract class LoanCommand {
    protected LoanList loans;

    public LoanCommand(LoanList loans) {
        this.loans = loans;
    }

    public void execute() {
    }
}
