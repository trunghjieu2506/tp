package people;

import java.util.ArrayList;

public class Person {
    protected final String name;
    protected String contactNumber;
    protected String eMail;
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
        return eMail;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }
}
