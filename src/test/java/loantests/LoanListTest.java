package loantests;

import loanbook.LoanList;
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
import loanbook.parsers.LoanCommandParser;
import loanbook.save.LoanSaveManager;
import utils.money.Money;
import org.junit.jupiter.api.Test;
import utils.people.PeopleList;
import utils.people.Person;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

public class LoanListTest {
    public LoanList loanList;

    @Test
    public void testOne() {
        LoanList loanList = new LoanList();
        Person George = new Person("George");
        Person Miao = new Person("Miao");
        Money moneyOne = new Money("SGD", 100);
        Money moneyTwo = new Money("USD", 123.33);

        LoanCommand command_one = new AddSimpleBulletLoanCommand(loanList, "money one", George, Miao,
                moneyOne);
        command_one.execute();
        LoanCommand command_two = new AddSimpleBulletLoanCommand(loanList, "money two", Miao, George,
                moneyTwo);
        command_two.execute();

        ListLoansCommand command_list = new ListLoansCommand(loanList);
        command_list.execute();

        ShowLoanDetailCommand command_detail = new ShowLoanDetailCommand(loanList, 1);
        command_detail.execute();

        Loan first = loanList.get(1);
        first.setStart(LocalDate.parse("2025-01-01"));

        PeopleList.findName("George").setContactNumber("12345678");
        George.setEmail("12345677@nus.com");
        PeopleList.findName("Miao").setEmail("miao@a.b.com");

        command_list.execute();
        command_detail.execute();
    }

    private LoanList testList() {
        new Person("George").addTag("Student");
        new Person("Miao").addTag("Parent");
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

        Person George = PeopleList.findOrAddPerson("George");
        Person Miao = PeopleList.findOrAddPerson("Miao");
        Person DBS = PeopleList.findOrAddPerson("DBS Bank");
        Person friend = PeopleList.findOrAddPerson("freundin");

        ArrayList<Loan> existingLoans = new ArrayList<>();
        existingLoans.add(new AdvancedLoan("testOne", George, Miao,
                money1, LocalDate.of(2025, 1, 1), interest1));
        existingLoans.add(new AdvancedLoan("testOne", George, Miao,
                money2, LocalDate.of(2022, 11, 1), interest2));
        existingLoans.add(new AdvancedLoan("testOne", friend, George,
                money3, LocalDate.of(2023, 3, 1), interest3));
        existingLoans.add(new AdvancedLoan("testOne", Miao, George,
                money4, LocalDate.of(2020, 5, 1), interest4));
        existingLoans.add(new AdvancedLoan("testOne", DBS, Miao,
                money5, LocalDate.of(2019, 6, 1), interest5));
        existingLoans.add(new AdvancedLoan("testOne", DBS, friend,
                money6, LocalDate.of(2001, 12, 1), interest6));
        existingLoans.add(new AdvancedLoan("testOne", George, Miao,
                money2, LocalDate.of(1975, 3, 1), interest1));
        existingLoans.add(new AdvancedLoan("testOne", George, Miao,
                money4, LocalDate.of(2024, 1, 1), interest2));

        return new LoanList(existingLoans);
    }

    @Test
    public void testTwo() {
        LoanList loanList = new LoanList();
        new Person("George").addTag("Student");
        new Person("Miao").addTag("Parent");
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

        Person George = PeopleList.findName("George");
        Person Miao = PeopleList.findName("Miao");

        LoanCommand command_one = new AddAdvancedLoanCommand(loanList, "testOne", George, Miao,
                moneyOne, LocalDate.of(2025, 1, 1), interest_one);
        command_one.execute();

        LoanCommand command_two = new AddAdvancedLoanCommand(loanList, "testTwo", Miao, George,
                moneyTwo, LocalDate.of(2023, 2, 20), interest_two);
        command_two.execute();

        LoanCommand command_3 = new AddAdvancedLoanCommand(loanList, "testTwo", George, Miao,
                money3, LocalDate.of(2021, 12, 25), interest_3);
        command_3.execute();

        LoanCommand command_4 = new AddAdvancedLoanCommand(loanList, "testTwo", Miao, George,
                money4, LocalDate.of(2001, 1, 30), interest_4);
        command_4.execute();

        LoanCommand command_5 = new AddAdvancedLoanCommand(loanList, "testTwo", Miao, George,
                money5, LocalDate.of(2016, 5, 15), interest_5);
        command_5.execute();

        LoanCommand command_6 = new AddAdvancedLoanCommand(loanList, "testTwo", Miao, George,
                money6, LocalDate.of(2024, 2, 20), interest_6);
        command_6.execute();

        ListLoansCommand command_list = new ListLoansCommand(loanList);

        System.out.println("Full List:");
        command_list.execute();
        System.out.println("Details of the first loan");
        ShowLoanDetailCommand command_7 = new ShowLoanDetailCommand(loanList, 1);
        command_7.execute();
        System.out.println("Outgoing loans of George");
        System.out.println(LoanList.forPrint(loanList.findOutgoingLoan(George)));
        System.out.println("Outgoing loans of Miao");
        System.out.println(LoanList.forPrint(loanList.findOutgoingLoan(Miao)));
        System.out.println("Remove loans[5]");
        DeleteLoanCommand command_8= new DeleteLoanCommand(loanList, 5);
        command_8.execute();
        command_list.execute();

    }

    @Test
    public void testUI() {
        LoanList loanList = new LoanList();
        String input = """
                e
                y
                George
                John
                wer
                123
                2025-13-13
                2025-1-1
                not interest
                COMPOUND 3% per month
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        LoanCommand command1 = LoanCommandParser.parse(loanList, scanner, "USD", "add");
        LoanCommand listCommand = new ListLoansCommand(loanList);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanList, 1);

        assert command1 != null;
        command1.execute();
        listCommand.execute();
        showCommand.execute();
    }

    @Test
    public void testSetCommands() {
        LoanList loanList = testList();

        String input = """
                
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        LoanCommand command1 = LoanCommandParser.parse(loanList, scanner, "USD", "edit 1 description");
        LoanCommand listCommand = new ListLoansCommand(loanList);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanList, 1);

        assert command1 != null;
        command1.execute();
        listCommand.execute();
        showCommand.execute();
    }

    @Test
    public void testFindCommands() {
        LoanList loanList = testList();

        String input = """
                
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        LoanCommand command1 = LoanCommandParser.parse(loanList, scanner, "USD", "find George outgoing loan");
        LoanCommand command2 = LoanCommandParser.parse(loanList, scanner, "USD", "find outgoing loan George");
        LoanCommand listCommand = new ListLoansCommand(loanList);
        LoanCommand showCommand = new ShowLoanDetailCommand(loanList, 1);

        assert command1 != null;
        assert command2 != null;
        command1.execute();
        command2.execute();
        listCommand.execute();
        showCommand.execute();
    }

    @Test
    public void testSave() {
        LoanList loanList = testList();
        try {
            LoanSaveManager.saveLoanList(loanList);
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
    }

    @Test
    public void testReadSave() {
        LoanList loanList;
        try {
            loanList = LoanSaveManager.readLoanList();
        } catch (FileNotFoundException e) {
            loanList = new LoanList();
            System.out.println("File not found");
        }
        System.out.println(loanList.simpleFulList());
    }
}
