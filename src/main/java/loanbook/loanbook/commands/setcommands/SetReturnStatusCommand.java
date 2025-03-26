package loanbook.loanbook.commands.setcommands;


import loanbook.loanbook.LoanList;

public class SetReturnStatusCommand extends SetCommand {
    protected boolean isReturned;

    public SetReturnStatusCommand(LoanList loanList, int index, boolean isReturned) {
        super(loanList, index);
        this.isReturned = isReturned;
    }

    @Override
    public void execute() {
        loan.setReturnStatus(isReturned);
        System.out.println("The return status of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
