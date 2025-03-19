package loanbook.loanbook.commands;

import loanbook.loanbook.LoanList;

public class ListCommand extends LoanCommand {
    protected LoanList loans;

    public ListCommand(LoanList loans) {
        this.loans = loans;
    }

    @Override
    public void execute() {
        System.out.println(loans.simpleFulList());
    }
}
