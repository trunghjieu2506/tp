package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;

public class FindTaggedLoanCommand extends FindLoanCommand {
    protected String tag;

    public FindTaggedLoanCommand(LoanManager loanManager, String tag) {
        super(loanManager);
        this.tag = tag;
    }

    @Override
    public void execute() {
        found = loanManager.findLoanWithTag(tag);
        if (found == null) {
            System.out.println("No results found");
        } else {
            System.out.println("Here is the list of loanManager with \"" + tag + "\" tag:");
            System.out.println(LoanUI.forPrint(found));
        }
    }
}
