package utils.contacts;

import utils.savemanager.SaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ContactsSaveManager extends SaveManager {
    public static final String CONTACTS_SEPARATOR = "<ContactsSaveSeparator>\n";
    public static final String NAME = "<Name>";
    public static final String CONTACT_NUMBER = "<ContactNumber>";
    public static final String EMAIL = "<EMail>";
    public static final String TAGS = "<Tags>";
    public static final String CONTACTS_SAVE_FOLDER = "save/people";
    public static final String SAVE_FILE_SUFFIX = "_contacts.txt";

    private static HashMap<String, Person> readSaveString(String user, String save) {
        HashMap<String, Person> saved = new HashMap<>();
        String[] splitPerson = save.split(CONTACTS_SEPARATOR);
        for (String personString : splitPerson) {
            Person person = readPerson(personString);
            if (person != null) {
                saved.put(person.getName(), person);
            }
        }
        return saved;
    }

    private static Person readPerson(String save) {
        Person person;
        String[] split = save.split("\n");
        String name = null;
        String contactNumber = null;
        String email = null;
        ArrayList<String> tagList = null;
        if (split[0].startsWith(NAME)) {
            name = split[0].replace(NAME, "").trim();
        }
        if (split[1].startsWith(CONTACT_NUMBER)) {
            contactNumber = split[1].replace(CONTACT_NUMBER, "").trim();
        }
        if (split[2].startsWith(EMAIL)) {
            email = split[2].replace(EMAIL, "").trim();
        }
        if (split[3].startsWith(TAGS)) {
            String[] tags = split[3].replace(TAGS, "").split(", ");
            tagList = new ArrayList<>(Arrays.asList(tags));
        }
        if (name != null) {
            person = new Person(name);
            person.setContactNumber(contactNumber);
            try {
                person.setEmail(email);
            } catch (IllegalArgumentException e) {
                person.setEmail(null);
            }
            person.addTags(tagList);
            return person;
        }
        return null;
    }

    public static ContactsList readSave(String user) throws FileNotFoundException {
        String save = getSaveString(savePath(user));
        return new ContactsList(user, readSaveString(user, save));
    }

    public static void savePeopleList(ContactsList contactsList) throws IOException {
        String path = savePath(contactsList.user);
        writeTextFile(CONTACTS_SAVE_FOLDER, path, contactsList.toSave());
    }

    public static void appendSave(ContactsList contactsList, String text) throws IOException {
        appendTextFile(CONTACTS_SAVE_FOLDER, savePath(contactsList.user), text);
    }

    private static String savePath(String user) {
        return CONTACTS_SAVE_FOLDER + '/' + user + SAVE_FILE_SUFFIX;
    }
}
