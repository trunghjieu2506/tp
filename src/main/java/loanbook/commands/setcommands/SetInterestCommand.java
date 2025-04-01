package loanbook.commands.setcommands;

import loanbook.LoanManager;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedLoan;

public class SetInterestCommand extends SetCommand {
    protected Interest interest;
    protected AdvancedLoan advancedLoan;

    public SetInterestCommand(LoanManager loanManager, int index, Interest interest) {
        super(loanManager, index);
        this.interest = interest;
        try {
            this.advancedLoan = (AdvancedLoan) loan;
        } catch (ClassCastException e) {
            this.advancedLoan = null;
        }
    }

    @Override
    public void execute() {
        if (loan instanceof AdvancedLoan) {
            advancedLoan.setInterest(interest);
            System.out.println("The interest of the following loan is updated:");
            System.out.println(loan.showDetails());
        } else {
            System.out.print("Error: Cannot apply interest to a simple loan\n> ");
        }
    }
}
