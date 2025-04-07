package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;


public class FindOverdueLoanCommand extends FindLoanCommand {

    public FindOverdueLoanCommand(LoanManager loanManager) {
        super(loanManager);
    }

    @Override
    public void execute() {
        found = loanManager.findOverdueLoan();
        if (found.isEmpty()) {
            System.out.println("No results found");
        } else {
            System.out.println("Overdue loan" + (found.size() == 1 ? "" : "s") +
                    (found.size() == 1 ? " is:" : " are:"));
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
