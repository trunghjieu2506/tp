package utils.contacts;

import loanbook.loan.Loan;
import utils.tags.Taggable;

import java.util.ArrayList;

/**
 * Stores information about a person, including the name, contact number, e-mail, and tags.
 * <code>name</code> cannot be changed once instantiated. The tags are automatically traced in
 *     <code>ContactsList</code>.
 */
public class Person implements Taggable {
    protected final String name;
    protected String contactNumber;
    protected String email;
    protected ArrayList<String> myTags;
    protected ArrayList<Loan> debtList;
    protected ArrayList<Loan> lentList;

    public Person(String name) {
        this.name = name;
        myTags = new ArrayList<>();
    }

    public Person(String name, String tag) {
        this.name = name;
        myTags = new ArrayList<>();
        myTags.add(tag);
    }

    public Person(String name, ArrayList<String> tags) {
        this.name = name;
        myTags = tags;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEMail() {
        return email;
    }

    public boolean hasTag(String tag) {
        return myTags.contains(tag);
    }

    public void addTag(String tag) {
        if (!myTags.contains(tag)) {
            myTags.add(tag);
        }
    }

    public void addTags(ArrayList<String> tags) {
        if (tags != null) {
            myTags.addAll(tags);
        }
    }

    public void removeTag(String tag) {
        myTags.remove(tag);
    }

    public void removeAllTags() {
        myTags.clear();
    }

    public ArrayList<String> getTagList() {
        return myTags;
    }

    public void setContactNumber(String number) {
        this.contactNumber = number;
    }

    public void addDebt(Loan loan) {
        if (loan.borrower() == this) {
            debtList.add(loan);
        }
    }

    public void addLent(Loan loan) {
        if (loan.lender() == this) {
            lentList.add(loan);
        }
    }

    public ArrayList<Loan> getDebtList() {
        return new ArrayList<>(debtList);
    }

    public ArrayList<Loan> getLentList() {
        return new ArrayList<>(lentList);
    }

    /**
     * Sets the person's <code>email</code>.
     * @param email the email set to the person's details.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return an <code>ArrayList</code> all tags that this person has.
     */
    public ArrayList<String> getTags() {
        return new ArrayList<>(myTags);
    }

    /**
     * @return a <code>String</code> of all tags that this person has. In one line, separated by commas.
     */
    public String showTags() {
        if (myTags == null || myTags.isEmpty()) {
            return "None";
        }
        StringBuilder output = new StringBuilder();
        int i = 0;
        for (String tag : myTags) {
            output.append(tag);
            if (i < (myTags.size() - 1)) {
                output.append(", ");
            }
            i++;
        }
        return output.toString();
    }

    /**
     * Shows the details of this person. Tags and <code>null</code> information are omitted.
     * @return a <code>String</code> containing the person's details in one line.
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(name);
        if (contactNumber != null) {
            output.append(" (Contact Number: ").append(contactNumber).append(")");
        }
        if (email != null) {
            output.append(" (E-Mail: ").append(email).append(")");
        }
        return output.toString();
    }

    /**
     * Shows every detail of the person. Each detail in a new line.
     * @return a ready-to-print <code>String</code> containing all information of this person.
     */
    public String showDetails() {
        return "Name: " + name + '\n'
                + "Contact Number: " + (contactNumber == null ? "None" : contactNumber) + '\n'
                + "E-Mail: " + (email == null ? "None" : email) + '\n'
                + "Tags: " + showTags() + '\n';
    }

    public String forSave() {
        return "<Name>" + name + '\n' +
                "<ContactNumber>" + (contactNumber == null ? "None" : contactNumber) + '\n' +
                "<EMail>" + (email == null ? "None" : email) + '\n' +
                "<Tags>" + showTags();
    }
}
