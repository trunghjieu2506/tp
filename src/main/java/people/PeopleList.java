package people;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores the list of <code>Person</code> that can be used
 */
public class PeopleList {
    public static Person me;
    protected static HashMap<String, Person> contacts;

    public static Person me(){
        return me;
    }

    public Person get(String name) throws IndexOutOfBoundsException {
        return contacts.get(name);
    }

    public void add(String name) {
        contacts.put(name, new Person(name));
    }

    public Person findName(String name) {
        return contacts.get(name);
    }

    public ArrayList<Person> findTag(String tag) {
        ArrayList<Person> found = new ArrayList<>();
        for (HashMap.Entry<String, Person> person : contacts.entrySet()) {
            if (person.getValue().hasTag(tag)) {
                found.add(person.getValue());
            }
        }
        return found;
    }
}
