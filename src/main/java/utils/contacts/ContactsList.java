package utils.contacts;

import utils.tags.TagList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Stores the list of <code>Person</code> that can be used.
 */
public class ContactsList {
    protected HashMap<String, Person> contacts;
    protected TagList<Person> tags;

    public ContactsList() {
        contacts = new HashMap<>();
        tags = new TagList<>();
    }

    public ContactsList(HashMap<String, Person> people) {
        contacts = people;
        initialiseTags();
    }

    public void addPerson(Person person) throws SameNameException {
        if (contacts.containsKey(person.getName())) {
            throw new SameNameException("This person already exists!");
        }
        ArrayList<String> tags = person.getTagList();
        for (String tag : tags) {
            this.tags.addMap(tag, person);
        }
        contacts.put(person.getName(), person);
    }

    public void addTag(String name, String tag) throws PersonNotFoundException {
        Person person = contacts.get(name);
        if (person == null) {
            throw new PersonNotFoundException("Person not found");
        }
        person.addTag(tag);
        tags.addMap(tag, person);
    }

    public void addTag(Person person, String tag) throws PersonNotFoundException {
        if (person == null || !contacts.containsValue(person)) {
            throw new PersonNotFoundException("Person not found");
        }
        person.addTag(tag);
        tags.addMap(tag, person);
    }

    public void remove(String name) throws PersonNotFoundException {
        Person person = contacts.get(name);
        if (person == null) {
            throw new PersonNotFoundException("Person not found");
        }
        tags.removeObject(person);
        contacts.remove(name);
    }

    public void remove(Person person) throws PersonNotFoundException {
        if (person == null) {
            throw new PersonNotFoundException("Person not found");
        }
        tags.removeObject(person);
        contacts.remove(person.getName());
    }

    public boolean hasPerson(String name) {
        return contacts.containsKey(name);
    }

    /**
     * @param name the name of the person to be found.
     * @return the <code>Person</code> with the input <code>name</code>. If the name is not in the contact list, return
     *     <code>null</code>.
     */
    public Person findName(String name) throws PersonNotFoundException {
        if (contacts.get(name) == null) {
            throw new PersonNotFoundException("Person not found");
        }
        return contacts.get(name);
    }

    public Person findOrAdd(String name) throws EmptyNameException {
        if (name.isBlank()) {
            throw new EmptyNameException("Name cannot be empty");
        }
        if (contacts.get(name) == null) {
            Person person = new Person(name);
            contacts.put(name, person);
            return person;
        }
        return contacts.get((name));
    }

    public void initialiseTags() {
        tags = new TagList<>();
        for (String name : contacts.keySet()) {
            Person person = contacts.get(name);
            assert person.getTagList() != null;
            for (String tag : person.getTagList()) {
                tags.addMap(tag, person);
            }
        }
    }

    /**
     * @param tag the tag of people to be found.
     * @return an <code>ArrayList</code> of all people with the <code>tag</code>.
     */
    public ArrayList<Person> findPeopleWithTag(String tag) {
        return tags.findWithTag(tag);
    }

    /**
     * Returns the ready-to-print <code>String</code> of all people with the <code>tag</code>. If there are less than
     * 5 people, they are shown in one line. If more than 5 people, they are printed in separate lines.
     * @param tag the tag that each person has.
     * @return a String containing the name of all people with the tag.
     */
    public String listPeopleWithTag(String tag) {
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
    public String listAll() {
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

    public String toSave() {
        StringBuilder save = new StringBuilder();
        ArrayList<String> names = new ArrayList<>(contacts.keySet());
        for (String name : names) {
            save.append(contacts.get(name).forSave()).append("\n").append(ContactsSaveManager.CONTACTS_SEPARATOR);
        }
        return save.toString();
    }
}
