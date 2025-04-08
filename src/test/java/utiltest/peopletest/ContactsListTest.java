package utiltest.peopletest;

import org.junit.jupiter.api.Test;
import utils.contacts.ContactsList;
import utils.contacts.ContactsUI;
import utils.contacts.Person;
import utils.contacts.SameNameException;
import utils.contacts.ContactsSaveManager;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsListTest {
    private ContactsList createContactList() {
        ContactsList contactsList = new ContactsList();
        String[] tags1 = {"Student", "Year 1", "CS"};
        ArrayList<String> tagList = new ArrayList<>(List.of(tags1));

        Person George = new Person("George");
        Person Miao = new Person("Miao");
        Person Liao = new Person("Liao");
        Person Giao = new Person("Giao");
        Person Robert = new Person("Robert");
        Person Siao = new Person("Siao", "Student");
        Person Biao = new Person("Biao", tagList);
        Person Ciao = new Person("Ciao", tagList);

        George.addTag("Student");
        Miao.addTag("Student");
        George.addTag("W11-2");
        Liao.addTag("W11-2");
        Robert.addTag("Student");
        George.setEmail("george@example.com");
        George.setContactNumber("12345678");

        contactsList.addPerson(George);
        contactsList.addPerson(Miao);
        contactsList.addPerson(Liao);
        contactsList.addPerson(Giao);
        contactsList.addPerson(Robert);
        contactsList.addPerson(Siao);
        contactsList.addPerson(Biao);
        contactsList.addPerson(Ciao);
        return contactsList;
    }

    @Test
    public void testOne() {
        ContactsList contactsList = createContactList();

        try {
            contactsList.addPerson(new Person("Miao", "W11-3"));
        } catch (SameNameException e) {
            System.out.println("Same Name Exception thrown");
        }

        System.out.println(contactsList.listPeopleWithTag("Student"));
        contactsList.remove("Giao");
        contactsList.remove("Miao");
        contactsList.remove("Robert");
        contactsList.remove("Liao");
        System.out.println(contactsList.listPeopleWithTag("Student"));
        System.out.println(contactsList.listAll());
    }

    @Test
    public void testSave() {
        ContactsList contactsList0 = createContactList();
        try {
            ContactsSaveManager.savePeopleList(contactsList0);
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
        System.out.println(contactsList0.listAll());
    }

    @Test
    public void testReadSave() {
        ContactsList contactsList;
        try {
            contactsList = ContactsSaveManager.readSave();
            System.out.println(contactsList.listAll());
            System.out.println(contactsList.findName("George").showDetails());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    @Test
    public void testAddPersonUI() {
        ContactsList contactsList = createContactList();
        String input = """
                lender
                N/A
                233@erd
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Person person = ContactsUI.handlePersonInputUI(contactsList, scanner, "", null);
        assertEquals("lender", person.getName());
    }
}
