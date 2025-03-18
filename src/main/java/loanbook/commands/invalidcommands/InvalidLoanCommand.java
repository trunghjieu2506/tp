package loanbook.commands.invalidcommands;

import loanbook.commands.LoanCommand;

public class InvalidLoanCommand extends LoanCommand{
    public InvalidLoanCommand() {
        super(null);
    }

    @Override
    public void execute() {
        System.out.println("Invalid Loan Command");
    }
}
