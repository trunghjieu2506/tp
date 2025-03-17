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
    protected final Person lender;
    protected final Person borrower;
    protected Money principal;
    protected boolean isReturned;
    protected LocalDate startDate;
    protected LocalDate returnDate;
    protected ArrayList<String> tags;

    public Loan(String description, Person lender, Person borrower, Money money) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.principal = money;
        this.isReturned = false;
    }

    public Loan(String description, Person lender, Person borrower, Money money, LocalDate returnDate) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.principal = money;
        this.returnDate = returnDate;
        this.isReturned = false;
    }

    public Loan(String description, Person lender, Person borrower, Money money, LocalDate startDate, LocalDate returnDate) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.principal = money;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.isReturned = false;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person borrower() {
        return borrower;
    }

    public Person lender() {
        return lender;
    }

    public Money principal() {
        return principal;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturnStatus(boolean isReturned) {
        this.isReturned = isReturned;
    }

    public void setStart(LocalDate date) {
        this.startDate = date;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public void setReturnDate(LocalDate date) {
        returnDate = date;
    }

    public LocalDate returnDate() {
        return returnDate;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void deleteTag(String tag) {
        tags.remove(tag);
    }

    public ArrayList<String> getTagsList() {
        if (tags == null) return null;
        return new ArrayList<>(tags);
    }

    public String getTags() {
        if (tags == null || tags.isEmpty()) return "None";
        StringBuilder output = new StringBuilder();
        int i = 0;
        for (String tag : tags) {
            output.append(tag);
            if (i < tags.size() - 1) {
                output.append(", ");
            }
        }
        return output.toString();
    }

    public String basicInfo() {
        return "Lender: " + lender.getName()
                + " Borrower: " + borrower.getName()
                + " Amount: " + principal.toString();
    }

    public String showDetails() {
        return "Lender: " + lender + '\n'
                + "Borrower: " + borrower + '\n'
                + "Amount: " + principal + '\n'
                + "Start Date: " + (startDate == null ? "None" : startDate) + '\n'
                + "Return Date: " + (returnDate == null ? "None" : returnDate) + '\n'
                + "Description: " + description + '\n'
                + "Tags: " + getTags() + '\n';
    }

    public String forSave() {
        return null;
    }
}
