package loanbook.commands.setcommands;

import loanbook.LoanManager;

import java.time.LocalDate;

public class SetStartDateCommand extends SetCommand {
    protected LocalDate newStartDate;

    public SetStartDateCommand(LoanManager loanManager, int index, LocalDate startDate) {
        super(loanManager, index);
        this.newStartDate = startDate;
    }

    @Override
    public void execute() {
        loan.setStart(newStartDate);
        System.out.println("The start date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
