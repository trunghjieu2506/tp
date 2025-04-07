package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import utils.io.IOHandler;

import java.util.Scanner;

import static utils.textcolour.TextColour.RED;

public class FindUrgentLoanCommand extends FindLoanCommand {
    protected int count;

    public FindUrgentLoanCommand(LoanManager loanManager, int count) {
        super(loanManager);
        this.count = count;
    }

    @Override
    public void execute() {
        found = loanManager.findUrgentLoan(count);
        if (found == null) {
            System.out.println("No results found");
        } else {
            int actualCount = Math.min(count, found.size());
            System.out.println("Here " + (count == 1 ? "is" : "are") + " the top " + actualCount + " urgent loan" +
                    (count == 1 ? "" : "s"));
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
