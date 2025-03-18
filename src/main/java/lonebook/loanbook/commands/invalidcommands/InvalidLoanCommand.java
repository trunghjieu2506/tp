package lonebook.loanbook.commands.invalidcommands;

import lonebook.loanbook.commands.LoanCommand;

public class InvalidLoanCommand extends LoanCommand{
    public InvalidLoanCommand() {
        super(null);
    }

    @Override
    public void execute() {
        System.out.println("Invalid Loan Command");
    }
}
