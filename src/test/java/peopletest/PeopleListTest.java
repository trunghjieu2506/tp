package peopletest;

import org.junit.jupiter.api.Test;
import utils.people.PeopleList;
import utils.people.Person;
import utils.people.SameNameException;

public class PeopleListTest {
    @Test
    public void testOne() {
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

        try {
            new Person("Miao").addTag("W11-3");
        } catch (SameNameException e) {
            System.out.println("Same Name Exception thrown");
        }

        System.out.println(PeopleList.listPeopleWithTag("Student"));
        PeopleList.remove("Giao");
        PeopleList.remove("Miao");
        PeopleList.remove(Robert);
        PeopleList.remove(Liao);
        System.out.println(PeopleList.listPeopleWithTag("Student"));
        System.out.println(PeopleList.listAll());
    }
}
