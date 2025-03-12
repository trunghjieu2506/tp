package loanbook.commands;

import loanbook.LoanList;

public class DeleteLoanCommand extends LoanCommand{
    protected LoanList loans;
    protected int index;

    @Override
    public void execute() {
        loans.delete(index);
    }
}
