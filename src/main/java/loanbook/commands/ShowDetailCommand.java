package loanbook.commands;

import loanbook.LoanList;

public class ShowDetailCommand extends LoanCommand {
    protected int index;

    public ShowDetailCommand(LoanList loans, int index) {
        super(loans);
        this.index = index;
    }

    @Override
    public void execute() {
        System.out.println(loans.showDetail(index));
    }
}
