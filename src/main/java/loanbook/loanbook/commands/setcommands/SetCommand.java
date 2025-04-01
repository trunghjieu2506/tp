package loanbook.loanbook.commands.setcommands;

import loanbook.loanbook.LoanList;
import loanbook.loanbook.commands.LoanCommand;
import loanbook.loanbook.loan.Loan;

public abstract class SetCommand extends LoanCommand {
    protected LoanList loanList;
    protected int index;
    protected Loan loan;

    public SetCommand(LoanList loanList, int index) {
        super();
        this.loanList = loanList;
        this.index = index;
        this.loan = loanList.get(index);
    }
}
