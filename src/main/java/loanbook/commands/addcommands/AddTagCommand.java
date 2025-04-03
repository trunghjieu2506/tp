package loanbook.commands.addcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;

public class AddTagCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected int index;
    protected String tag;
    protected Loan loan;

    public AddTagCommand(LoanManager loanManager, int index, String tag) {
        this.loanManager = loanManager;
        this.index = index;
        this.tag = tag;
        this.loan = loanManager.get(index);
    }

    @Override
    public void execute() {
        loanManager.addTag(index, tag);
        System.out.println("Tag [" + tag + "] has been added to the following loan:");
        System.out.println(loan.showDetails());
    }
}
