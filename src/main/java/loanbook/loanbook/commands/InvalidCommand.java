package loanbook.loanbook.commands;

public class InvalidCommand extends LoanCommand {
    protected String message;
    public InvalidCommand(String message) {
    }

    @Override
    public void execute() {
        System.out.println(message);
    }
}
