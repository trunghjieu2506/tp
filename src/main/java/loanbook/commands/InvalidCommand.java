package loanbook.commands;

public class InvalidCommand extends LoanCommand {
    protected String message;
    public InvalidCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        if (message != null) {
            System.out.println(message);
        }
    }
}
