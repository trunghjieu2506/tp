package loanbook.commands.findcommands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;

public class FindTaggedLoanCommand extends FindLoanCommand {
    protected LoanManager loanManager;
    protected String tag;

    public FindTaggedLoanCommand(LoanManager loanManager, String tag) {
        super(loanManager);
        this.tag = tag;
    }

    @Override
    public void execute() {
        found = loanManager.findLoanWithTag(tag);
        if (found.isEmpty()) {
            System.out.println("No results found");
        } else {
            System.out.println("Here are the list of loans with \"" + tag + "\" tag:");
            System.out.println(LoanUI.forPrint(loanManager.findLoanWithTag(tag)));
        }
    }
}
