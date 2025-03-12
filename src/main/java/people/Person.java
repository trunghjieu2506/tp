package people;

import java.util.ArrayList;

public class Person {
    protected final String name;
    protected String contactNumber;
    protected String email;
    protected ArrayList<String> tags;

    public Person(String name) {
        this.name = name;
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
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public void setContactNumber(String number) {
        this.contactNumber = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
