package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;

public class SimpleLoan extends Loan {
    public SimpleLoan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        super(description, lender, borrower, money, deadline);
    }
}
