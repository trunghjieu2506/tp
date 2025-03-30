package loanbook.loanbook.commands.setcommands;

import loanbook.loanbook.LoanList;

public class SetDescriptionCommand extends SetCommand {
    protected String newDescription;

    public SetDescriptionCommand(LoanList loanList, int index, String description) {
        super(loanList, index);
        this.newDescription = description;
    }

    @Override
    public void execute() {
        loan.setDescription(newDescription);
        System.out.println("The description of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
