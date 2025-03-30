package utils.people;

import utils.tags.TagList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Stores the list of <code>Person</code> that can be used. All people are added to this list once instantiated.
 */
public class PeopleList {
    protected static Person me = null;
    protected static HashMap<String, Person> contacts = new HashMap<>();
    protected static TagList<Person> tags = new TagList<>();

    public static Person me(){
        return me;
    }

    public static void setMe(Person person) {
        me = person;
    }

    public static void setMe(String name) {
        me = contacts.get(name);
    }

    public static void add(Person person) {
        contacts.put(person.getName(), person);
    }

    public static void remove(Person person) {
        if (person != null) {
            person.removeAllTags();
            contacts.remove(person.getName());
        }
    }

    public static void remove(String name) {
        Person person = contacts.get(name);
        if (person != null) {
            contacts.get(name).removeAllTags();
            contacts.remove(name);
        }
    }

    /**
     * @param name the name of the person to be found.
     * @return the <code>Person</code> with the input <code>name</code>. If the name is not in the contact list, return
     *     <code>null</code>.
     */
    public static Person findName(String name) {
        return contacts.get(name);
    }

    public static Person findOrAddPerson(String name) {
        if (contacts.get(name) == null) {
            return new Person(name);
        }
        return contacts.get((name));
    }

    /**
     * @param tag the tag of people to be found.
     * @return an <code>ArrayList</code> of all people with the <code>tag</code>.
     */
    public static ArrayList<Person> peopleWithTag(String tag) {
        return tags.findWithTag(tag);
    }

    /**
     * Returns the ready-to-print <code>String</code> of all people with the <code>tag</code>. If there are less than
     * 5 people, they are shown in one line. If more than 5 people, they are printed in separate lines.
     * @param tag the tag that each person has.
     * @return a String containing the name of all people with the tag.
     */
    public static String listPeopleWithTag(String tag) {
        ArrayList<Person> list = tags.findWithTag(tag);
        if (list == null) {
            return "Not results";
        }
        StringBuilder output = new StringBuilder();
        if (list.size() <= 5) {
            int i = 0;
            for (Person person : list) {
                output.append(person.getName());
                if (i < list.size() - 1) {
                    output.append(", ");
                }
                i++;
            }
        } else {
            int i = 1;
            for (Person person : list) {
                output.append("[" + i + "] ").append(person.getName()).append('\n');
                i++;
            }
        }
        return output.toString();
    }

    /**
     * Outputs the ready-to-print list of all people's names in alphabetical order.
     * @return a <code>String</code> of all names. Each name takes a new line.
     */
    public static String listAll() {
        ArrayList<String> names = new ArrayList<>(contacts.keySet());
        Collections.sort(names);
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (String name : names) {
            output.append("[" + i + "] ").append(name).append('\n');
            i++;
        }
        return output.toString();
    }

    public static String toSave() {
        StringBuilder save = new StringBuilder();
        ArrayList<String> names = new ArrayList<>(contacts.keySet());
        for (String name : names) {
            save.append(contacts.get(name).forSave()).append("\n").append(PeopleSaveManager.PEOPLE_SEPARATOR);
        }
        return save.toString();
    }
}
