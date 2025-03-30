package loanbook.commands.setcommands;

import loanbook.LoanList;

import java.time.LocalDate;

public class SetStartDateCommand extends SetCommand {
    protected LocalDate newStartDate;

    public SetStartDateCommand(LoanList loanList, int index, LocalDate startDate) {
        super(loanList, index);
        this.newStartDate = startDate;
    }

    @Override
    public void execute() {
        loan.setStart(newStartDate);
        System.out.println("The start date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
