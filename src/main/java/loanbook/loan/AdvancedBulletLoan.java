package loanbook.loan;

import loanbook.interest.Interest;
import money.Money;
import people.Person;

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
