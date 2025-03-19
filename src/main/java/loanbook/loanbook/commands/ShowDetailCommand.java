package loanbook.loanbook.commands;

import loanbook.loanbook.LoanList;

public class ShowDetailCommand extends LoanCommand {
    protected LoanList loans;
    protected int index;

    public ShowDetailCommand(LoanList loans, int index) {
        this.loans = loans;
        this.index = index;
    }

    @Override
    public void execute() {
        System.out.println(loans.showDetail(index));
    }
}
