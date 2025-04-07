package loanbook.loan;

import cashflow.model.interfaces.Finance;
import utils.money.Money;
import utils.contacts.Person;
import utils.tags.Taggable;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Stores basic information about a loan.
 */
public abstract class Loan extends Finance implements Taggable {
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
        tags = new ArrayList<>();
    }

    public Loan(String description, Person lender, Person borrower, Money money, LocalDate returnDate) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.principal = money;
        this.returnDate = returnDate;
        this.isReturned = false;
        tags = new ArrayList<>();
    }

    public Loan(String description, Person lender, Person borrower, Money money,
                LocalDate startDate, LocalDate returnDate) {
        this.description = description;
        this.lender = lender;
        this.borrower = borrower;
        this.principal = money;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.isReturned = false;
        tags = new ArrayList<>();
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

    public void setPrincipal(Money money) {
        principal = money;
    }

    public abstract Money getBalance();

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturnStatus(boolean isReturned) {
        this.isReturned = isReturned;
    }

    public void setStartDate(LocalDate date) {
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
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void addTags(ArrayList<String> tags) {
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public void removeAllTags() {
        tags.clear();
    }

    public ArrayList<String> getTagList() {
        if (tags == null) {
            return null;
        }
        return new ArrayList<>(tags);
    }

    /**
     * Generates a <code>String</code> to be printed when showing all tags of this loan.
     * @return the <code>String</code> generated.
     */
    public String getTagsString() {
        if (tags == null || tags.isEmpty()) {
            return "None";
        }
        StringBuilder output = new StringBuilder();
        int i = 0;
        for (String tag : tags) {
            output.append(tag);
            if (i < tags.size() - 1) {
                output.append(", ");
            }
            i++;
        }
        return output.toString();
    }

    /**
     * Checks whether this loan is overdue.
     * @return <code>true</code> only if <code>returnDate</code> is defined and is before the day this method is called.
     */
    public boolean isOverdue() {
        if (returnDate == null) {
            return false;
        }
        return LocalDate.now().isAfter(returnDate);
    }

    /**
     * Basic information of this loan.
     * @return a ready-to-print <code>String</code> containing all basic information. Multiple lines.
     */
    public String basicInfo() {
        return "Lender: [" + lender.getName()
                + "] Borrower: [" + borrower.getName() + '\n'
                + "] Amount: " + principal
                + (isReturned ? "    Returned" : "    Not Returned");
    }

    /**
     * Shows all details of this loan.
     * @return a ready-to-print <code>String</code> containing all information. Multiple lines.
     */
    public String showDetails() {
        return "Lender: " + lender + '\n'
                + "Borrower: " + borrower + '\n'
                + "Amount: " + principal + '\n'
                + "Start Date: " + (startDate == null ? "None" : startDate) + '\n'
                + "Return Date: " + (returnDate == null ? "None" : returnDate) + '\n'
                + "Description: " + (description == null ? "None" : description) + '\n'
                + "Tags: " + getTagsString() + '\n';
    }

    /**
     * Generates a <code>String</code> that can be stored and read easily, containing all information of this loan.
     * @return the <code>String</code> generated.
     */
    public String forSave() {
        return "<Lender>" + lender + '\n' +
                "<Borrower>" + borrower + '\n' +
                "<Principal>" + principal + '\n' +
                "<StartDate>" + (startDate == null ? "None" : startDate) + '\n' +
                "<ReturnDate>" + (returnDate == null ? "None" : returnDate) + '\n' +
                "<Description>" + (description == null ? "None" : description) + '\n' +
                "<Tags>" + getTagsString() + '\n';
    }
}
