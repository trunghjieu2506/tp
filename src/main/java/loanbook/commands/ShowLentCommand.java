package loanbook.commands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import utils.contacts.PersonNotFoundException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

public class ShowLentCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected String name;

    public ShowLentCommand(LoanManager loanManager, String name) {
        this.loanManager = loanManager;
        this.name = name;
    }

    @Override
    public void execute() {
        try {
            HashMap<Currency, BigDecimal> lents = loanManager.getTotalLent(name);
            if (lents.isEmpty()) {
                System.out.println("No loanManager found");
            } else {
                System.out.println("Total lent loanManager for [" + name + "] are:");
                System.out.print(LoanUI.forPrint(lents));
            }
        } catch (PersonNotFoundException e) {
            System.out.println("Person not found");
        }
    }
}
