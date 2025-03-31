package loanbook.commands.setcommands;

import loanbook.LoanManager;
import utils.money.Money;

public class SetPrincipalCommand extends SetCommand{
    protected Money newPrincipal;

    public SetPrincipalCommand(LoanManager loanManager, int index, Money money) {
        super(loanManager, index);
        this.newPrincipal = money;
    }

    @Override
    public void execute() {
        loan.setPrincipal(newPrincipal);
        System.out.println("The principal of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
