package loanbook.commands.setcommands;

import loanbook.LoanManager;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedBulletLoan;

public class SetInterestCommand extends SetCommand {
    protected Interest interest;
    protected AdvancedBulletLoan advancedBulletLoan;

    public SetInterestCommand(LoanManager loanManager, int index, Interest interest) {
        super(loanManager, index);
        this.interest = interest;
        try {
            this.advancedBulletLoan = (AdvancedBulletLoan) loan;
        } catch (ClassCastException e) {
            this.advancedBulletLoan = null;
        }
    }

    @Override
    public void execute() {
        if (loan instanceof AdvancedBulletLoan) {
            advancedBulletLoan.setInterest(interest);
            System.out.println("The interest of the following loan is updated:");
            System.out.println(loan.showDetails());
        } else {
            System.out.print("Error: Cannot apply interest to a simple loan\n> ");
        }
    }
}
