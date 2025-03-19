package loanbook.loanbook.loan;

import loanbook.loanbook.interest.Interest;
import utils.money.Money;
import utils.people.Person;

public class AdvancedBulletLoan extends SimpleBulletLoan {
    protected Interest interest;
    protected Money outstandingBalance;

    public AdvancedBulletLoan(String description, Person lender, Person borrower, Money money, Interest interest) {
        super(description, lender, borrower, money);
        this.interest = interest;
    }

    @Override
    public String forSave() {
        return "ABL" + super.forSave();
    }
}
