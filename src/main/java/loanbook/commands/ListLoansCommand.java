package loanbook.commands;

import loanbook.LoanManager;

public class ListLoansCommand extends LoanCommand {
    protected LoanManager loans;

    public ListLoansCommand(LoanManager loans) {
        this.loans = loans;
    }

    @Override
    public void execute() {
        System.out.println(loans.simpleFulList());
    }
}
