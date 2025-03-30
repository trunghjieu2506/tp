package utils.savemanager;

import utils.people.PeopleList;
import utils.people.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PeopleSaveManager extends SaveManager{
    public static final String PEOPLE_SEPARATOR = "<PersonSaveSeparator>\n";
    public static final String NAME = "<Name>";
    public static final String CONTACT_NUMBER = "<ContactNumber>";
    public static final String EMAIL = "<EMail>";
    public static final String TAGS = "<Tags>";
    public static final String PEOPLE_SAVE_PATH = "save/peopleData.txt";

    private static void readSaveString(String save) {
        String[] splitPerson = save.split(PEOPLE_SEPARATOR);
        for (String personString : splitPerson) {
            readPerson(personString);
        }
    }

    private static void readPerson(String save) {
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
            Person person = PeopleList.findOrAddPerson(name);
            person.setContactNumber(contactNumber);
            try {
                person.setEmail(email);
            } catch (IllegalArgumentException ignored) {
            }
            person.addTags(tagList);
        }
    }

    public static void readSave() throws FileNotFoundException {
        String save = getSaveString(PEOPLE_SAVE_PATH);
        readSaveString(save);
    }

    public static void savePeopleList() throws IOException {
        writeTextFile(PEOPLE_SAVE_PATH, PeopleList.toSave());
    }

    public static void appendSave(String text) throws IOException {
        appendTextFile(PEOPLE_SAVE_PATH, text);
    }
}
