package loanbook.loanbook.commands.setcommands;

import loanbook.loanbook.LoanList;

import java.time.LocalDate;

public class SetReturnDateCommand extends SetCommand {
    protected LocalDate newReturnDate;

    public SetReturnDateCommand(LoanList loanList, int index, LocalDate returnDate) {
        super(loanList, index);
        this.newReturnDate = returnDate;
    }

    @Override
    public void execute() {
        loan.setReturnDate(newReturnDate);
        System.out.println("The return date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
