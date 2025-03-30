package utils.people;

import utils.tags.TagList;

import java.util.ArrayList;

/**
 * Stores information about a person, including the name, contact number, e-mail, and tags.
 * <code>name</code> cannot be changed once instantiated. The tags are automatically traced in <code>PeopleList</code>.
 */
public class Person {
    protected final String name;
    protected String contactNumber;
    protected String email;
    protected ArrayList<String> myTags;
    protected TagList<Person> personTagList = PeopleList.tags;

    public Person(String name) throws SameNameException {
        if (PeopleList.contacts.containsKey(name)) {
            throw new SameNameException("This person already exists!");
        }
        this.name = name;
        myTags = new ArrayList<>();
        PeopleList.add(this);
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
        myTags.add(tag);
        personTagList.addMap(tag, this);
    }

    public void addTags(ArrayList<String> tags) {
        if (tags != null) {
            for (String tag : tags) {
                myTags.add(tag);
                personTagList.addMap(tag, this);
            }
        }
    }

    public void removeTag(String tag) {
        myTags.remove(tag);
        personTagList.removeMap(tag, this);
    }

    public void removeAllTags() {
        for (String tag : myTags) {
            personTagList.removeMap(tag, this);
        }
    }

    public void setContactNumber(String number) {
        this.contactNumber = number;
    }

    /**
     * Sets the person's <code>email</code>.
     * @param email the email set to the person's details.
     * @throws IllegalArgumentException if the input <code>String</code> does not contain "@".
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Incorrect E-Mail format");
        }
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
