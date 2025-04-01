package loanbook.commands.setcommands;


import loanbook.LoanManager;

public class SetReturnStatusCommand extends SetCommand {
    protected boolean isReturned;

    public SetReturnStatusCommand(LoanManager loanManager, int index, boolean isReturned) {
        super(loanManager, index);
        this.isReturned = isReturned;
    }

    @Override
    public void execute() {
        loan.setReturnStatus(isReturned);
        System.out.println("The return status of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
