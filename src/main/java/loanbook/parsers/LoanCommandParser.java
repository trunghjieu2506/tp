package loanbook.parsers;

import loanbook.LoanList;
import loanbook.commands.DeleteLoanCommand;
import loanbook.commands.InvalidCommand;
import loanbook.commands.ListLoansCommand;
import loanbook.commands.LoanCommand;
import loanbook.commands.ShowLoanDetailCommand;
import loanbook.commands.addcommands.AddAdvancedLoanCommand;
import loanbook.commands.addcommands.AddSimpleBulletLoanCommand;
import loanbook.commands.findcommands.FindOutgoingLoanCommand;
import loanbook.commands.setcommands.SetDescriptionCommand;
import loanbook.commands.setcommands.SetInterestCommand;
import loanbook.commands.setcommands.SetPrincipalCommand;
import loanbook.commands.setcommands.SetReturnDateCommand;
import loanbook.commands.setcommands.SetReturnStatusCommand;
import loanbook.commands.setcommands.SetStartDateCommand;
import loanbook.interest.Interest;
import loanbook.loan.SimpleBulletLoan;
import utils.datetime.DateParser;
import utils.money.Money;
import utils.money.MoneyParser;
import utils.people.PeopleList;
import utils.people.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

public class LoanCommandParser {
    public static LoanCommand parse(LoanList loanList, Scanner scanner, String defaultCurrency, String input) {
        String[] splitFirst = input.split(" ", 2);
        String firstWord = splitFirst[0].trim();
        try {
            switch (firstWord) {
            case "list":
                return new ListLoansCommand(loanList);
            case "delete":
                int deleteIndex = Integer.parseInt(splitFirst[1]);
                return new DeleteLoanCommand(loanList, deleteIndex);
            case "add":
                return handleAddLoanCommand(loanList, scanner, Currency.getInstance(defaultCurrency.toUpperCase()));
            case "show":
                int showIndex = Integer.parseInt(splitFirst[1]);
                return new ShowLoanDetailCommand(loanList, showIndex);
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
                int index;
                try {
                    index = Integer.parseInt(split[0]);
                } catch (NumberFormatException e) {
                    return new InvalidCommand("What part of which loan are you editing? Format: edit X [attribute]");
                }
                String attribute = split[1];
                return handleSetCommand(loanList, scanner, index, attribute, defaultCurrency);
            case "find":
                if (splitFirst.length == 1) {
                    return new InvalidCommand("What are you looking for?");
                }
                return handleFindCommand(loanList, scanner, splitFirst[1]);
            default:
                return null;
            }
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            return null;
        }
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
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency,
                        "Key in the amount of money lent:");
                String description = handleDescriptionInputUI(scanner);
                LocalDate returnDate = DateParser.handleLocalDateUI(scanner,
                        "Key in the return date of the loan (yyyy-mm-dd)", true);
                return new AddSimpleBulletLoanCommand(loanList, description, lender, borrower, money, returnDate);
            } else if (mode.equals("y")) {
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency,
                        "Key in the amount of principal:");
                LocalDate startDate = DateParser.handleLocalDateUI(scanner,
                        "Key in the start date of the loan (yyyy-mm-dd):");
                LocalDate returnDate = DateParser.handleLocalDateUI(scanner,
                        "Key in the return date of the loan (yyyy-mm-dd)", true);
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                assert interest != null;
                String description = handleDescriptionInputUI(scanner);
                return new AddAdvancedLoanCommand(loanList, description, lender, borrower, money, startDate, returnDate, interest);
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
        case "lender":
        case "borrower":
            return new InvalidCommand("You cannot edit the lender or borrower.");
        case "description":
            System.out.print("Key in the new description:\n> ");
            String description = scanner.nextLine();
            if (description.equalsIgnoreCase("N/A")) {
                description = null;
            }
            return new SetDescriptionCommand(loanList, index, description);
        case "start date":
            LocalDate startDate = DateParser.handleLocalDateUI(scanner, "Key in the new start date:");
            return new SetStartDateCommand(loanList, index, startDate);
        case "return date":
            LocalDate returnDate = DateParser.handleLocalDateUI(scanner, "Key in the new return date:");
            return new SetReturnDateCommand(loanList, index, returnDate);
        case "principal":
        case "amount":
            String instruction = "Key in the amount" + (attribute.equals("principal") ? "of principal" : "");
            Money money = MoneyParser.handleMoneyInputUI(scanner, Currency.getInstance(defaultCurrency), instruction);
            return new SetPrincipalCommand(loanList, index, money);
        case "interest":
            if (loanList.get(index) instanceof SimpleBulletLoan) {
                return new InvalidCommand("You cannot edit a simple loan.\n> ");
            } else {
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                return new SetInterestCommand(loanList, index, interest);
            }
        default:
            return null;
        }
    }

    private static String handleDescriptionInputUI(Scanner scanner) {
        System.out.print("Key in the description (Key in \"N/A\" if not applicable):" + "\n> ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("N/A")) {
            return null;
        }
        return input;
    }

    private static LoanCommand handleFindCommand(LoanList loanList, Scanner scanner, String input) {
        if (input.contains("outgoing loan")) {
            String name = input.replace("outgoing loan", "");
            System.out.println("Name is " + name);
            return new FindOutgoingLoanCommand(loanList, name.trim());
        } else {
            Person person = PeopleList.findName(input.trim());
            if (person != null) {

            }
        }
        return null;
    }

    private static ArrayList<String> handleAddTagsUI(Scanner scanner) {
        ArrayList<String> output = new ArrayList<>();
        while (true) {
            System.out.print("Key in a tag (Key in \"N/A\" if not applicable");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("N/A")) {
                break;
            }
            output.add(input);
        }
        return output;
    }
}
