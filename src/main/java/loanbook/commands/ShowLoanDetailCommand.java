package loanbook.commands;

import loanbook.LoanList;

public class ShowLoanDetailCommand extends LoanCommand {
    protected LoanList loans;
    protected int index;

    public ShowLoanDetailCommand(LoanList loans, int index) {
        this.loans = loans;
        this.index = index;
    }

    @Override
    public void execute() {
        try {
            System.out.println(loans.showDetail(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid loan index");
        }
    }
}
