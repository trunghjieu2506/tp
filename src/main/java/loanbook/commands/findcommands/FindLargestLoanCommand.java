package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;

public class FindLargestLoanCommand extends FindLoanCommand{
    int count;

    public FindLargestLoanCommand(LoanManager loanManager, int count) {
        super(loanManager);
        this.count = count;
    }

    @Override
    public void execute() {
        found = loanManager.findLargestLoans(count);
        if (found == null) {
            System.out.println("No results found");
        } else {
            int actualCount = Math.min(count, found.size());
            System.out.println("Here " + (count == 1 ? "is" : "are") + " the top " + actualCount + " largest loan" +
                    (count == 1 ? "" : "s"));
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
