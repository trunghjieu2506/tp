package loanbook.commands.setcommands;

import loanbook.LoanManager;

public class SetDescriptionCommand extends SetCommand {
    protected String newDescription;

    public SetDescriptionCommand(LoanManager loanManager, int index, String description) {
        super(loanManager, index);
        this.newDescription = description;
    }

    @Override
    public void execute() {
        loan.setDescription(newDescription);
        loanManager.storeLoans();
        System.out.println("The description of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
