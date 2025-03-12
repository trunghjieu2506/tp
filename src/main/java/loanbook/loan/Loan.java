package loanbook.loan;

import money.Money;
import people.Person;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Stores basic information about a loan.
 */
public abstract class Loan {
    protected String description;
    protected Person lender;
    protected Person borrower;
    protected Money money;
    protected boolean isReturned;
    protected LocalDate start;
    protected LocalDate deadline;
    protected ArrayList<String> tags;

    public Loan(String description, Person lender, Person borrower, Money money) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.start = LocalDate.now();
        this.isReturned = false;
    }

    public Loan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.money = money;
        this.start = LocalDate.now();
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

    public void setReturnStatus(boolean isReturned) {
        this.isReturned = isReturned;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setDeadline(LocalDate date) {
        deadline = date;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public String forSave() {
        return null;
    }
}
