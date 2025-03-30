package peopletest;

import org.junit.jupiter.api.Test;
import utils.people.PeopleList;
import utils.people.Person;
import utils.people.SameNameException;
import utils.savemanager.PeopleSaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PeopleListTest {
    private void createPeopleList() {
        Person George = new Person("George");
        Person Miao = new Person("Miao");
        Person Liao = new Person("Liao");
        Person Giao = new Person("Giao");
        Person Robert = new Person("Robert");
        new Person("Siao").addTag("Student");
        new Person("Diao").addTag("Student");
        new Person("Biao").addTag("Student");
        new Person("Ciao").addTag("Student");
        new Person("Qiao").addTag("Student");

        George.addTag("Student");
        Miao.addTag("Student");
        George.addTag("W11-2");
        Liao.addTag("W11-2");
        Robert.addTag("Student");
        George.setEmail("george@example.com");
        George.setContactNumber("12345678");
    }

    @Test
    public void testOne() {
        createPeopleList();

        try {
            new Person("Miao").addTag("W11-3");
        } catch (SameNameException e) {
            System.out.println("Same Name Exception thrown");
        }

        System.out.println(PeopleList.listPeopleWithTag("Student"));
        PeopleList.remove("Giao");
        PeopleList.remove("Miao");
        PeopleList.remove("Robert");
        PeopleList.remove("Liao");
        System.out.println(PeopleList.listPeopleWithTag("Student"));
        System.out.println(PeopleList.listAll());
    }

    @Test
    public void testSave() {
        createPeopleList();
        try {
            PeopleSaveManager.savePeopleList();
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
        System.out.println(PeopleList.listAll());
    }

    @Test
    public void testReadSave() {
        try {
            PeopleSaveManager.readSave();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        System.out.println(PeopleList.listAll());
        System.out.println(PeopleList.findName("George").showDetails());
    }
}
