package loanbook.commands.setcommands;

import loanbook.LoanList;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;

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
