package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Loan {
    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;
    protected boolean isReturned;
    protected LocalDate deadline;
    protected ArrayList<String> tags;

    public Loan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.deadline = deadline;
        this.isReturned = false;
    }

    public String getDescription() {
        return description;
    }

    public Person getBorrower() {
        return borrower;
    }

    public Person getLender() {
        return lender;
    }

    public Money getMoney() {
        return money;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }
}
