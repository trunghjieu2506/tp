package loanbook.commands;

import loanbook.LoanList;

public class ListCommand extends LoanCommand {

    public ListCommand(LoanList loans) {
        super(loans);
    }

    @Override
    public void execute() {
        System.out.println(loans.simpleFulList());
    }
}
