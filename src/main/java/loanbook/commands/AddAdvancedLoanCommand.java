package loanbook.commands;

import money.Money;
import people.Person;

import java.time.LocalDate;
import java.time.Period;

public class AddAdvancedLoanCommand extends AddLoanCommand{
    protected Period period;
    protected double interest;

    public AddAdvancedLoanCommand(String description, Person lender, Person borrower, Money money, Period period, double interest) {
        super(description, lender, borrower, money);
        this.period = period;
        this.interest = interest;
    }

    public AddAdvancedLoanCommand(String description, Person lender, Person borrower, Money money, LocalDate deadline, Period period, double interest) {
        super(description, lender, borrower, money, deadline);
        this.period = period;
        this.interest = interest;
    }

    @Override
    public void execute() {
        if (deadline == null) {
            loans.add(description, lender, borrower, money, period, interest);
        } else {
            loans.add(description, lender, borrower, money, deadline, period, interest);
        }
    }
}
