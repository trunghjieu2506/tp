package loanbook.commands.setcommands;

import loanbook.LoanManager;

import java.time.LocalDate;

public class SetReturnDateCommand extends SetCommand {
    protected LocalDate newReturnDate;

    public SetReturnDateCommand(LoanManager loanManager, int index, LocalDate returnDate) {
        super(loanManager, index);
        this.newReturnDate = returnDate;
    }

    @Override
    public void execute() {
        loan.setReturnDate(newReturnDate);
        System.out.println("The return date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
