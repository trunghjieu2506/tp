package loanbook.loanbook.parsers;

import loanbook.loanbook.LoanList;
import loanbook.loanbook.commands.*;
import loanbook.loanbook.commands.addcommands.AddAdvancedLoanCommand;
import loanbook.loanbook.commands.addcommands.AddSimpleBulletLoanCommand;
import loanbook.loanbook.commands.setcommands.*;
import loanbook.loanbook.interest.Interest;
import loanbook.loanbook.loan.SimpleBulletLoan;
import utils.datetime.DateParser;
import utils.money.Money;
import utils.money.MoneyParser;
import utils.people.PeopleList;
import utils.people.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;
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
                return handleAddLoanCommand(loanList, scanner, Currency.getInstance(defaultCurrency.toUpperCase()));
            case "show":
                int showIndex = Integer.parseInt(splitFirst[1]);
                return new ShowDetailCommand(loanList, showIndex);
            case "return":
                int returnIndex = Integer.parseInt(splitFirst[1]);
                return new SetReturnStatusCommand(loanList, returnIndex, true);
            case "unreturn":
                int unReturnIndex = Integer.parseInt(splitFirst[1]);
                return new SetReturnStatusCommand(loanList, unReturnIndex, false);
            case "edit":
                if (splitFirst.length == 1) {
                    return new InvalidCommand("What part of which loan are you editing? Format: edit X [attribute]");
                }
                String[] split = splitFirst[1].split(" ", 2);
                int index = Integer.parseInt(split[0]);
                String attribute = split[1];
                return handleSetCommand(loanList, scanner, index, attribute, defaultCurrency);
            }
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            return null;
        }
        return null;
    }

    private static LoanCommand handleAddLoanCommand(LoanList loanList, Scanner scanner, Currency currency) {
        String mode;
        System.out.print("With or without interest? (y/n)\n> ");
        mode = scanner.nextLine();
        while (!(mode.equalsIgnoreCase("y") || mode.equalsIgnoreCase("n"))) {
            System.out.print("Input y or n only!\n> ");
            mode = scanner.nextLine();
        }
        System.out.print("Enter the lender's name:\n> ");
        Person lender = PeopleList.findOrAddPerson(scanner.nextLine());
        System.out.print("Enter the borrower's name:\n> ");
        Person borrower = PeopleList.findOrAddPerson(scanner.nextLine());

        try {
            if (mode.equals("n")) {
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency, "Key in the amount of money lent:");
                return new AddSimpleBulletLoanCommand(loanList, lender, borrower, money);
            } else if (mode.equals("y")) {
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency, "Key in the amount of principal:");
                LocalDate startDate = DateParser.handleLocalDateUI(scanner, "Key in the start date of the loan (yyyy-mm-dd):");
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                if (interest == null) {
                    return null;
                }
                return new AddAdvancedLoanCommand(loanList, lender, borrower, money, startDate, interest);
            }
        } catch (NullPointerException | NumberFormatException e) {
            return new InvalidCommand("Invalid number input");
        } catch (DateTimeParseException e) {
            return new InvalidCommand("Invalid date format");
        }
        return null;
    }

    private static LoanCommand handleSetCommand(LoanList loanList, Scanner scanner, int index, String attribute, String defaultCurrency) {
        switch (attribute) {
        case "description":
            System.out.print("Key in the new description:\n> ");
            String description = scanner.nextLine();
            return new SetDescriptionCommand(loanList, index, description);
        case "start date":
            LocalDate startDate = DateParser.handleLocalDateUI(scanner, "Key in the new start date:");
            return new SetStartDateCommand(loanList, index, startDate);
        case "return date":
            LocalDate returnDate = DateParser.handleLocalDateUI(scanner, "Key in the new return date:");
            return new SetReturnDateCommand(loanList, index, returnDate);
        case "principal":
            String instruction = "Key in the amount of principal:";
        case "amount":
            instruction = "Key in the amount:";
            Money money = MoneyParser.handleMoneyInputUI(scanner, Currency.getInstance(defaultCurrency), instruction);
            return new SetPrincipalCommand(loanList, index, money);
        case "interest":
            if (loanList.get(index) instanceof SimpleBulletLoan) {
                return new InvalidCommand("You cannot edit a simple loan.\n> ");
            } else {
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                return new SetInterestCommand(loanList, index, interest);
            }
        }
        return null;
    }
}
