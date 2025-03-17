package loantests;

import loanbook.LoanList;
import loanbook.commands.*;
import loanbook.interest.Interest;
import loanbook.interest.InterestType;
import loanbook.loan.Loan;
import money.Money;
import org.junit.jupiter.api.Test;
import people.PeopleList;
import people.Person;

import java.time.LocalDate;
import java.time.Period;

public class LoanListTest {
    public LoanList loanList;

    @Test
    public void testOne() {
        LoanList loanList = new LoanList();
        Person George = new Person("George");
        Person Miao = new Person("Miao");
        Money moneyOne = new Money("SGD", 100);
        Money moneyTwo = new Money("USD", 123.33);

        LoanCommand command_one = new AddSimpleBulletLoanCommand(loanList, "money one", George, Miao, moneyOne);
        command_one.execute();
        LoanCommand command_two = new AddSimpleBulletLoanCommand(loanList, "money two", Miao, George, moneyTwo);
        command_two.execute();

        ListCommand command_list = new ListCommand(loanList);
        command_list.execute();

        ShowDetailCommand command_detail = new ShowDetailCommand(loanList, 1);
        command_detail.execute();

        Loan first = loanList.get(1);
        first.setStart(LocalDate.parse("2025-01-01"));

        PeopleList.findName("George").setContactNumber("12345678");
        George.setEmail("12345677@nus.com");
        PeopleList.findName("Miao").setEmail("miao@a.b.com");

        command_list.execute();
        command_detail.execute();
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

        ListCommand command_list = new ListCommand(loanList);

        System.out.println("Full List:");
        command_list.execute();
        System.out.println("Details of the first loan");
        ShowDetailCommand command_7 = new ShowDetailCommand(loanList, 1);
        command_7.execute();
        System.out.println("Outgoing loans of George");
        System.out.println(loanList.forPrint(loanList.findOutgoingLoan(George)));
        System.out.println("Outgoing loans of Miao");
        System.out.println(loanList.forPrint(loanList.findOutgoingLoan(Miao)));
        System.out.println("Remove loans[5]");
        DeleteLoanCommand command_8= new DeleteLoanCommand(loanList, 5);
        command_8.execute();
        command_list.execute();

    }
}
