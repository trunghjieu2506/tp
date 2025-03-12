package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;
import java.time.Period;

public class AdvancedLoan extends SimpleLoan {
    protected Period period;
    protected double interest;

    public AdvancedLoan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        super(description, lender, borrower, money, deadline);
    }
}
