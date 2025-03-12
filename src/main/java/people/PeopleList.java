package people;

import java.util.ArrayList;

public class PeopleList {
    protected ArrayList<Person> contacts;

    public Person get(int index) throws IndexOutOfBoundsException {
        return contacts.get(index);
    }

    public ArrayList<Person> findName(String name) {
        ArrayList<Person> found = new ArrayList<>();
        for (Person person : contacts) {
            if (person.getName().equalsIgnoreCase(name.trim())) {
                found.add(person);
            }
        }
        return found;
    }

    public ArrayList<Person> findTag(String tag) {
        ArrayList<Person> found = new ArrayList<>();
        for (Person person : contacts) {
            if (person.hasTag(tag)) {
                found.add(person);
            }
        }
        return found;
    }
}
