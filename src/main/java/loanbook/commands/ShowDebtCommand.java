package loanbook.commands;

import loanbook.LoanManager;
import loanbook.ui.LoanUI;
import utils.contacts.PersonNotFoundException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

public class ShowDebtCommand extends LoanCommand {
    protected LoanManager loanManager;
    protected String name;

    public ShowDebtCommand(LoanManager loanManager, String name) {
        this.loanManager = loanManager;
        this.name = name;
    }

    @Override
    public void execute() {
        try {
            HashMap<Currency, BigDecimal> debts = loanManager.getTotalDebt(name);
            if (debts.isEmpty()) {
                System.out.println("No debts found");
            } else {
                System.out.println("Total debts for [" + name + "] are:");
                System.out.print(LoanUI.forPrint(debts));
            }
        } catch (PersonNotFoundException e) {
            System.out.println("Person not found");
        }
    }
}
