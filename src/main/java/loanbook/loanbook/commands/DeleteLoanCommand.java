package loanbook.loanbook.commands;

import loanbook.loanbook.LoanList;

public class DeleteLoanCommand extends LoanCommand{
    protected LoanList loans;
    protected int index;

    public DeleteLoanCommand(LoanList loans, int index) {
        this.loans = loans;
        this.index = index;
    }

    @Override
    public void execute() {
        loans.delete(index);
    }
}
