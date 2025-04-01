package loantests;

import loanbook.LoanManager;
import loanbook.commands.DeleteLoanCommand;
import loanbook.commands.ListLoansCommand;
import loanbook.commands.LoanCommand;
import loanbook.commands.ShowLoanDetailCommand;
import loanbook.commands.addcommands.AddAdvancedLoanCommand;
import loanbook.commands.addcommands.AddSimpleBulletLoanCommand;
import loanbook.interest.Interest;
import loanbook.interest.InterestType;
import loanbook.loan.AdvancedLoan;
import loanbook.loan.Loan;
import loanbook.loan.SimpleBulletLoan;
import loanbook.parsers.LoanCommandParser;
import loanbook.save.LoanSaveManager;
import utils.money.Money;
import org.junit.jupiter.api.Test;
import utils.contacts.ContactsList;
import utils.contacts.Person;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

public class LoanListTest {
    public LoanManager loanManager;

    @Test
    public void testOne() {
        LoanManager loanManager = new LoanManager("GeorgeMiao");
        ContactsList contactsList = loanManager.getContactsList();
        Person George = new Person("George");
        Person Miao = new Person("Miao");
        contactsList.addPerson(George);
        contactsList.addPerson(Miao);
        Money moneyOne = new Money("SGD", 100);
        Money moneyTwo = new Money("USD", 123.33);

        LoanCommand command1 = new AddSimpleBulletLoanCommand(loanManager, "money one", George, Miao,
                moneyOne);
        command1.execute();
        LoanCommand command2 = new AddSimpleBulletLoanCommand(loanManager, "money two", Miao, George,
                moneyTwo);
        command2.execute();

        ListLoansCommand commandList = new ListLoansCommand(loanManager);
        commandList.execute();

        ShowLoanDetailCommand commandDetail = new ShowLoanDetailCommand(loanManager, 1);
        commandDetail.execute();

        Loan first = loanManager.get(1);
        first.setStart(LocalDate.parse("2025-01-01"));

        contactsList.findName("George").setContactNumber("12345678");
        George.setEmail("12345677@nus.com");
        contactsList.findName("Miao").setEmail("miao@a.b.com");

        commandList.execute();
        commandDetail.execute();
    }

    private ArrayList<Loan> testList(ContactsList contactsList) {
        contactsList.addPerson(new Person("George", "Student"));
        contactsList.addPerson(new Person("Miao", "Parent"));
        Money money1 = new Money("SGD", 100);
        Money money2 = new Money("USD", 123.33);
        Money money3 = new Money("CNY", 1122.33);
        Money money4 = new Money("GBP", 1024.00);
        Money money5 = new Money("SGD", 32768.00);
        Money money6 = new Money("JPY", 11232223.00);

        Interest interest1 = new Interest(5, InterestType.COMPOUND, Period.ofMonths(1));
        Interest interest2 = new Interest(3, InterestType.SIMPLE, Period.ofYears(1));
        Interest interest3 = new Interest(4.5, InterestType.COMPOUND, Period.ofMonths(1));
        Interest interest4 = new Interest(10, InterestType.SIMPLE, Period.ofYears(1));
        Interest interest5 = new Interest(1.5, InterestType.COMPOUND, Period.ofMonths(3));
        Interest interest6 = new Interest(3.3, InterestType.COMPOUND, Period.ofMonths(3));

        Person George = contactsList.findOrAdd("George");
        Person Miao = contactsList.findOrAdd("Miao");
        Person DBS = contactsList.findOrAdd("DBS Bank");
        Person friend = contactsList.findOrAdd("freundin");

        ArrayList<Loan> existingLoans = new ArrayList<>();
        existingLoans.add(new AdvancedLoan(null, George, Miao,
                money1, LocalDate.of(2025, 1, 1), interest1));
        existingLoans.add(new AdvancedLoan(null, George, Miao,
                money2, LocalDate.of(2022, 11, 1), interest2));
        existingLoans.add(new AdvancedLoan(null, friend, George,
                money3, LocalDate.of(2023, 3, 1), interest3));
        existingLoans.add(new AdvancedLoan("testOne", Miao, George,
                money4, LocalDate.of(2020, 5, 1), interest4));
        existingLoans.add(new AdvancedLoan("testOne", DBS, Miao,
                money5, LocalDate.of(2019, 6, 1), interest5));
        existingLoans.add(new AdvancedLoan("testOne", DBS, friend,
                money6, LocalDate.of(2001, 12, 1), interest6));
        existingLoans.add(new SimpleBulletLoan("testOne", George, Miao,
                money2, LocalDate.of(1975, 3, 1)));
        existingLoans.add(new SimpleBulletLoan("testOne", George, Miao,
                money4, LocalDate.of(2024, 1, 1)));

        return existingLoans;
    }

    @Test
    public void testTwo() {
        LoanManager loanManager = new LoanManager("Test Two");
        ContactsList contactsList = new ContactsList("Test Two");
        loanManager.setContactsList(contactsList);
        contactsList.addPerson(new Person("George", "Student"));
        contactsList.addPerson(new Person("Miao", "Parent"));
        Money moneyOne = new Money("SGD", 100);
        Money moneyTwo = new Money("USD", 123.33);
        Money money3 = new Money("CNY", 1122.33);
        Money money4 = new Money("GBP", 1024.00);
        Money money5 = new Money("SGD", 32768.00);
        Money money6 = new Money("JPY", 11232223.00);

        Interest interest_one = new Interest(5, InterestType.COMPOUND, Period.ofMonths(1));
        Interest interest_two = new Interest(3, InterestType.SIMPLE, Period.ofYears(1));
        Interest interest_3 = new Interest(4.5, InterestType.COMPOUND, Period.ofMonths(1));
        Interest interest_4 = new Interest(10, InterestType.SIMPLE, Period.ofYears(1));
        Interest interest_5 = new Interest(1.5, InterestType.COMPOUND, Period.ofMonths(3));
        Interest interest_6 = new Interest(3.3, InterestType.COMPOUND, Period.ofMonths(3));

        Person George = contactsList.findName("George");
        Person Miao = contactsList.findName("Miao");

        LoanCommand command_one = new AddAdvancedLoanCommand(loanManager, "testOne", George, Miao,
                moneyOne, LocalDate.of(2025, 1, 1), interest_one);
        command_one.execute();

        LoanCommand command_two = new AddAdvancedLoanCommand(loanManager, "testTwo", Miao, George,
                moneyTwo, LocalDate.of(2023, 2, 20), interest_two);
        command_two.execute();

        LoanCommand command_3 = new AddAdvancedLoanCommand(loanManager, "testTwo", George, Miao,
                money3, LocalDate.of(2021, 12, 25), interest_3);
        command_3.execute();

        LoanCommand command_4 = new AddAdvancedLoanCommand(loanManager, "testTwo", Miao, George,
                money4, LocalDate.of(2001, 1, 30), interest_4);
        command_4.execute();

        LoanCommand command_5 = new AddAdvancedLoanCommand(loanManager, "testTwo", Miao, George,
                money5, LocalDate.of(2016, 5, 15), interest_5);
        command_5.execute();

        LoanCommand command_6 = new AddAdvancedLoanCommand(loanManager, "testTwo", Miao, George,
                money6, LocalDate.of(2024, 2, 20), interest_6);
        command_6.execute();

        ListLoansCommand command_list = new ListLoansCommand(loanManager);

        System.out.println("Full List:");
        command_list.execute();
        System.out.println("Details of the first loan");
        ShowLoanDetailCommand command_7 = new ShowLoanDetailCommand(loanManager, 1);
        command_7.execute();
        System.out.println("Outgoing loans of George");
        System.out.println(LoanManager.forPrint(loanManager.findOutgoingLoan(George)));
        System.out.println("Outgoing loans of Miao");
        System.out.println(LoanManager.forPrint(loanManager.findOutgoingLoan(Miao)));
        System.out.println("Remove loans[5]");
        DeleteLoanCommand command_8= new DeleteLoanCommand(loanManager, 5);
        command_8.execute();
        command_list.execute();

    }

    @Test
    public void testUI() {
        LoanManager loanManager = new LoanManager("Test UI");
        String input = """
                e
                y
                George
                John
                wer
                123
                2025-13-13
                2025-1-1
                2025-03-10
                not interest
                COMPOUND 3% per month
                N/A
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        LoanCommand command1 = LoanCommandParser.parse(loanManager, scanner, "USD", "add");
        LoanCommand listCommand = new ListLoansCommand(loanManager);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanManager, 1);

        assert command1 != null;
        command1.execute();
        listCommand.execute();
        showCommand.execute();
    }

    @Test
    public void testSetCommands() {
        ContactsList contactsList = new ContactsList("Test_Set");
        LoanManager loanManager = new LoanManager("Test_Set", testList(contactsList), contactsList);

        String input = """
                Hello World
                2025-1-1
                2024-1-1
                SIMPLE 1% per 2 months
                50
                100
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        LoanCommand command1 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 description");
        LoanCommand command2 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 return date");
        LoanCommand command3 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 start date");
        LoanCommand command4 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 interest");
        LoanCommand command5 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 amount");
        LoanCommand command6 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "edit 1 principal");
        LoanCommand command7 = LoanCommandParser.parse(loanManager, scanner, "USD",
                "return 1");
        LoanCommand listCommand = new ListLoansCommand(loanManager);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanManager, 1);

        assert command1 != null;
        assert command2 != null;
        assert command3 != null;
        assert command4 != null;
        assert command5 != null;
        assert command6 != null;
        assert command7 != null;
        command1.execute();
        showCommand.execute();
        command2.execute();
        showCommand.execute();
        command3.execute();
        showCommand.execute();
        command4.execute();
        showCommand.execute();
        command5.execute();
        showCommand.execute();
        command6.execute();
        showCommand.execute();
        command7.execute();
        showCommand.execute();
        listCommand.execute();
    }

    @Test
    public void testFindCommands() {
        ContactsList contactsList = new ContactsList("Test_Find");
        LoanManager loanManager = new LoanManager("Test_Find", testList(contactsList), contactsList);

        String input = """
                
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        LoanCommand command0 = LoanCommandParser.parse(loanManager, scanner, "USD", "help find");
        LoanCommand command1 = LoanCommandParser.parse(loanManager, scanner, "USD", "find George outgoing loan");
        LoanCommand command2 = LoanCommandParser.parse(loanManager, scanner, "USD", "find outgoing loan George");
        LoanCommand command3 = LoanCommandParser.parse(loanManager, scanner, "USD", "find incoming loan George");
        LoanCommand command4 = LoanCommandParser.parse(loanManager, scanner, "USD", "find George");
        LoanCommand listCommand = new ListLoansCommand(loanManager);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanManager, 1);

        assert command0 != null;
        assert command1 != null;
        assert command2 != null;
        assert command3 != null;
        assert command4 != null;
        command0.execute();
        command1.execute();
        command2.execute();
        command3.execute();
        command4.execute();
        listCommand.execute();
        showCommand.execute();
    }

    @Test
    public void testSave() {
        ContactsList contactsList = new ContactsList("Test_Save");
        LoanManager loanManager = new LoanManager("Test_Save", testList(contactsList), contactsList);
        try {
            LoanSaveManager.saveLoanList(loanManager);
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
    }

    @Test
    public void testReadSave() {
        String user = "Test_Save";
        LoanManager loanManager;
        try {
            loanManager = LoanSaveManager.readLoanList(user);
        } catch (FileNotFoundException e) {
            loanManager = new LoanManager(user);
            System.out.println("File not found");
        }
        System.out.println(loanManager.simpleFulList());
        System.out.println(loanManager.showDetail(1));
    }
}
