package loanbook.commands.setcommands;

import loanbook.LoanManager;
import loanbook.commands.LoanCommand;
import loanbook.loan.Loan;

public abstract class SetCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected int index;
    protected Loan loan;

    public SetCommand(LoanManager loanManager, int index) {
        super();
        this.loanManager = loanManager;
        this.index = index;
        this.loan = loanManager.get(index);
    }
}
