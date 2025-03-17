package loantests;

import loanbook.LoanList;
import loanbook.commands.*;
import loanbook.interest.Interest;
import loanbook.loan.Loan;
import money.Money;
import org.junit.jupiter.api.Test;
import people.PeopleList;
import people.Person;

import java.time.LocalDate;

public class LoanListTest {
    public LoanList loanList;

    @Test
    public void testOne() {
        LoanList loanList = new LoanList();
        Person George = new Person("George");
        Person Miao = new Person("Miao");
        Money moneyOne = new Money("SGD", 100);
        Money moneyTwo = new Money("USD", 123.33);

        LoanCommand command_one = new AddLoanCommand(loanList, "money one", George, Miao, moneyOne);
        command_one.execute();
        LoanCommand command_two = new AddLoanCommand(loanList, "money two", Miao, George, moneyTwo);
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
}
