package loanbook.loanbook.parsers;

import loanbook.loanbook.LoanList;
import loanbook.loanbook.commands.*;
import loanbook.loanbook.interest.Interest;
import utils.datetime.DateParser;
import utils.money.Money;
import utils.people.PeopleList;
import utils.people.Person;

import java.time.LocalDate;
import java.util.Scanner;

public class LoanCommandParser {
    public static LoanCommand parse(LoanList loanList, Scanner scanner, String defaultCurrency, String input) {
        String[] splitFirst = input.split(" ", 2);
        String firstWord = splitFirst[0].trim();
        try {
            switch (firstWord) {
            case "list":
                return new ListCommand(loanList);
            case "delete":
                int deleteIndex = Integer.parseInt(splitFirst[1]);
                return new DeleteLoanCommand(loanList, deleteIndex);
            case "add":
                return handleAddLoanCommand(loanList, scanner, defaultCurrency);
            case "show":
                int showIndex = Integer.parseInt(splitFirst[1]);
                return new ShowDetailCommand(loanList, showIndex);
            }
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            return null;
        }
        return null;
    }

    private static LoanCommand handleAddLoanCommand(LoanList loanList, Scanner scanner, String currency) {
        String mode = "";
        System.out.println("With or without interest? (y/n)\n> ");
        mode = scanner.nextLine();
        while (!(mode.equalsIgnoreCase("y") || mode.equalsIgnoreCase("n"))) {
            System.out.println("Input y or n only!");
            mode = scanner.nextLine();
        }
        System.out.print("Enter the lender's name:\n> ");
        Person lender = PeopleList.findOrAddPerson(scanner.nextLine());
        System.out.print("Enter the borrower's name:\n> ");
        Person borrower = PeopleList.findOrAddPerson(scanner.nextLine());

        Money money;
        try {
            if (mode.equals("n")) {
                System.out.print("Key in the amount of money lent:\n> ");
                double amount = Double.parseDouble(scanner.nextLine());
                money = new Money(currency, amount);
                return new AddSimpleBulletLoanCommand(loanList, lender, borrower, money);
            } else if (mode.equals("y")) {
                System.out.print("Key in the amount of principal:\n> ");
                double moneyDigits = Double.parseDouble(scanner.nextLine());
                System.out.print("Key in the start date of the loan (yyyy-mm-dd):\n> ");
                LocalDate startDate = DateParser.parse(scanner.nextLine());
                money = new Money(currency, moneyDigits);
                System.out.print("Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):\n> ");
                Interest interest = InterestParser.parseInterest(scanner.nextLine());
                if (interest == null) {
                    return null;
                }
                return new AddAdvancedLoanCommand(loanList, lender, borrower, money, startDate, interest);
            }
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
        return null;
    }
}
