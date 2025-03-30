package loanbook.commands.addcommands;

import loanbook.LoanList;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;

public class AddTagCommand extends LoanCommand {
    protected LoanList loanList;
    protected int index;
    protected String tag;
    protected Loan loan;

    public AddTagCommand(LoanList loanList, int index, String tag) {
        this.loanList = loanList;
        this.index = index;
        this.tag = tag;
        this.loan = loanList.get(index);
    }

    @Override
    public void execute() {
        loanList.addTag(index, tag);
        System.out.println("The description of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
