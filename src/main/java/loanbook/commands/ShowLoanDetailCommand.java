package loanbook.commands;

import loanbook.LoanManager;

public class ShowLoanDetailCommand extends LoanCommand {
    protected LoanManager loans;
    protected int index;

    public ShowLoanDetailCommand(LoanManager loans, int index) {
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
