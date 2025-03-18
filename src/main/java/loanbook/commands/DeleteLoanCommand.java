package loanbook.commands;

import loanbook.LoanList;

public class DeleteLoanCommand extends LoanCommand{
    protected int index;

    public DeleteLoanCommand(LoanList loans, int index) {
        super(loans);
        this.index = index;
    }

    @Override
    public void execute() {
        loans.delete(index);
    }
}
