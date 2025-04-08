package loanbook.commands.setcommands;

import loanbook.LoanManager;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedBulletLoan;
import utils.io.IOHandler;

import static utils.textcolour.TextColour.RED;

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
            loanManager.storeLoans();
        } else {
            IOHandler.writeOutputWithColour("Cannot apply interest to a simple loan", RED);
        }
    }
}
