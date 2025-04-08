package loanbook.parsers;

import loanbook.LoanManager;
import loanbook.commands.DeleteLoanCommand;
import loanbook.commands.LoanHelpCommand;
import loanbook.commands.findcommands.FindLargestLoanCommand;
import loanbook.commands.findcommands.FindOverdueLoanCommand;
import loanbook.commands.findcommands.FindUrgentLoanCommand;
import loanbook.commands.printcommands.InvalidMessageCommand;
import loanbook.commands.printcommands.PrintMessageCommand;
import loanbook.commands.ListLoansCommand;
import loanbook.commands.LoanCommand;
import loanbook.commands.ShowLoanDetailCommand;
import loanbook.commands.addcommands.AddAdvancedBulletLoanCommand;
import loanbook.commands.addcommands.AddSimpleBulletLoanCommand;
import loanbook.commands.findcommands.FindAssociatedLoanCommand;
import loanbook.commands.findcommands.FindIncomingLoanCommand;
import loanbook.commands.findcommands.FindMyIncomingLoanCommand;
import loanbook.commands.findcommands.FindMyOutgoingLoanCommand;
import loanbook.commands.findcommands.FindOutgoingLoanCommand;
import loanbook.commands.findcommands.FindTaggedLoanCommand;
import loanbook.commands.setcommands.SetDescriptionCommand;
import loanbook.commands.setcommands.SetInterestCommand;
import loanbook.commands.setcommands.SetPrincipalCommand;
import loanbook.commands.setcommands.SetReturnDateCommand;
import loanbook.commands.setcommands.SetReturnStatusCommand;
import loanbook.commands.setcommands.SetStartDateCommand;
import loanbook.commands.setcommands.SetTagCommand;
import loanbook.interest.Interest;
import loanbook.loan.SimpleBulletLoan;
import utils.contacts.ContactsUI;
import utils.contacts.PersonNotFoundException;
import utils.datetime.DateParser;
import utils.io.IOHandler;
import utils.money.Money;
import utils.money.MoneyParser;
import utils.contacts.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

import static utils.contacts.ContactsUI.handlePersonInputUI;
import static utils.textcolour.TextColour.RED;

/**
 * This class parses the user input to generate commands. Asks the user for more input if necessary.
 */
public class LoanCommandParser {
    public static LoanCommand parse(LoanManager loanManager, Scanner scanner, Currency defaultCurrency, String input) {
        String[] splitFirst = input.split(" ", 2);
        String firstWord = splitFirst[0].trim();
        try {
            switch (firstWord) {
            case "list":
                return new ListLoansCommand(loanManager);
            case "delete":
                int deleteIndex = Integer.parseInt(splitFirst[1]);
                return new DeleteLoanCommand(loanManager, deleteIndex);
            case "add":
                return handleAddLoanCommandUI(loanManager, scanner, defaultCurrency);
            case "show":
                int showIndex = Integer.parseInt(splitFirst[1]);
                return new ShowLoanDetailCommand(loanManager, showIndex);
            case "return":
                if (splitFirst.length == 1) {
                    return new PrintMessageCommand("""
                            Which loan are you updating? Command format: "return X"
                            To view the list of loanManager, type "list".
                            """);
                }
                int returnIndex = Integer.parseInt(splitFirst[1]);
                return new SetReturnStatusCommand(loanManager, returnIndex, true);
            case "unreturn":
                if (splitFirst.length == 1) {
                    return new PrintMessageCommand("""
                            Which loan are you updating? Command format: "unreturn X"
                            To view the list of loanManager, type "list".
                            """);
                }
                int unReturnIndex = Integer.parseInt(splitFirst[1]);
                return new SetReturnStatusCommand(loanManager, unReturnIndex, false);
            case "edit":
                if (splitFirst.length == 1) {
                    return new PrintMessageCommand(
                            "What part of which loan are you editing? Format: edit X [attribute]");
                }
                String[] split = splitFirst[1].split(" ", 2);
                int index;
                try {
                    index = Integer.parseInt(split[0]);
                } catch (NumberFormatException e) {
                    return new PrintMessageCommand(
                            "What part of which loan are you editing? Format: edit X [attribute]");
                }
                String attribute = split[1];
                return handleSetCommandUI(loanManager, scanner, index, attribute, defaultCurrency);
            case "find":
                if (splitFirst.length == 1) {
                    return new PrintMessageCommand("What are you looking for? Type \"help find\" for help.");
                }
                return handleFindCommand(loanManager, scanner, splitFirst[1]);
            case "help":
                if (splitFirst.length == 1) {
                    return handleHelpCommand(null);
                } else {
                    return handleHelpCommand(splitFirst[1]);
                }
            default:
                return null;
            }
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public static LoanCommand parse(LoanManager loanManager, Scanner scanner, String defaultCurrency, String input) {
        return parse(loanManager, scanner, Currency.getInstance(defaultCurrency), input);
    }

    private static LoanCommand handleAddLoanCommandUI(LoanManager loanManager, Scanner scanner, Currency currency) {
        String mode;
        System.out.print("With or without interest? (y/n)\n> ");
        mode = scanner.nextLine();
        while (!(mode.equalsIgnoreCase("y") || mode.equalsIgnoreCase("n"))) {
            System.out.print("Input y or n only!\n> ");
            mode = scanner.nextLine();
        }
        Person lender = handlePersonInputUI(loanManager.getContactsList(), scanner,
                "Enter the lender's name:", null);
        Person borrower = ContactsUI.handlePersonInputUI(loanManager.getContactsList(), scanner,
                "Enter the borrower's name:", lender);
        try {
            if (mode.equals("n")) {
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency,
                        "Key in the amount of money lent:");
                String description = handleDescriptionInputUI(scanner);
                LocalDate startDate = DateParser.handleLocalDateUI(scanner,
                        "Key in the start date of the loan (yyyy-mm-dd)", true);
                LocalDate returnDate = DateParser.handleReturnDateUI(scanner,
                        "Key in the return date of the loan (yyyy-mm-dd)", startDate, true);
                ArrayList<String> tags = handleTagsInputUI(scanner);
                return new AddSimpleBulletLoanCommand(loanManager, description, lender, borrower, money, startDate,
                        returnDate, tags);
            } else if (mode.equals("y")) {
                Money money = MoneyParser.handleMoneyInputUI(scanner, currency,
                        "Key in the amount of principal:");
                LocalDate startDate = DateParser.handleLocalDateUI(scanner,
                        "Key in the start date of the loan (yyyy-mm-dd):");
                LocalDate returnDate = DateParser.handleReturnDateUI(scanner,
                        "Key in the return date of the loan (yyyy-mm-dd)", startDate, true);
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                assert interest != null;
                String description = handleDescriptionInputUI(scanner);
                ArrayList<String> tags = handleTagsInputUI(scanner);
                return new AddAdvancedBulletLoanCommand(loanManager, description, lender, borrower, money, startDate,
                        returnDate, interest, tags);
            }
        } catch (NullPointerException | NumberFormatException e) {
            return new InvalidMessageCommand("Invalid number input");
        } catch (DateTimeParseException e) {
            return new InvalidMessageCommand("Invalid date format");
        }
        return null;
    }

    private static LoanCommand handleSetCommandUI(LoanManager loanManager, Scanner scanner, int index, String attribute,
                                                  Currency defaultCurrency) {
        switch (attribute) {
        case "lender":
        case "borrower":
            return new PrintMessageCommand("You cannot edit the lender or borrower.");
        case "description":
            System.out.print("Key in the new description:\n> ");
            String description = scanner.nextLine();
            if (description.equalsIgnoreCase("N/A")) {
                description = null;
            }
            return new SetDescriptionCommand(loanManager, index, description);
        case "start date":
            return new SetStartDateCommand(loanManager, index, scanner, false);
        case "return date":
            return new SetReturnDateCommand(loanManager, index, scanner, false);
        case "principal":
        case "amount":
            String instruction = "Key in the amount" + (attribute.equals("principal") ? "of principal" : "");
            Money money = MoneyParser.handleMoneyInputUI(scanner, defaultCurrency, instruction);
            return new SetPrincipalCommand(loanManager, index, money);
        case "interest":
            if (loanManager.get(index) instanceof SimpleBulletLoan) {
                return new InvalidMessageCommand("A simple bullet loan does not apply interest.");
            } else {
                Interest interest = InterestParser.handleInterestInputUI(scanner);
                return new SetInterestCommand(loanManager, index, interest);
            }
        case "tag":
            return new SetTagCommand(loanManager, scanner, index);
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

    private static LoanCommand handleFindCommand(LoanManager loanManager, Scanner scanner, String input) {
        if (input.contains("outgoing loan")) {
            String name = input.replace("outgoing loan", "");
            if (name.isBlank()) {
                return new FindMyOutgoingLoanCommand(loanManager);
            }
            return new FindOutgoingLoanCommand(loanManager, name.trim());
        } else if (input.contains("incoming loan")) {
            String name = input.replace("incoming loan", "");
            if (name.isBlank()) {
                return new FindMyIncomingLoanCommand(loanManager);
            }
            return new FindIncomingLoanCommand(loanManager, name.trim());
        } else if (input.trim().startsWith("tag")){
            String tag = input.replace("tag", "").trim();
            return new FindTaggedLoanCommand(loanManager, tag);
        } else if (input.trim().equalsIgnoreCase("overdue loan")) {
            return new FindOverdueLoanCommand(loanManager);
        } else if (input.contains("urgent loan")) {
            int count;
            try {
                count = Integer.parseInt(input.replace("urgent loan", "").trim());
            } catch (NumberFormatException e) {
                count = 5;
            }
            return new FindUrgentLoanCommand(loanManager, count);
        } else if (input.contains("largest loan")) {
            int count;
            try {
                count = Integer.parseInt(input.replace("largest loan", "").trim());
            } catch (NumberFormatException e) {
                count = 5;
            }
            return new FindLargestLoanCommand(loanManager, count);
        } else {
            try {
                Person person = loanManager.getContactsList().findName(input.trim());
                return new FindAssociatedLoanCommand(loanManager, person);
            } catch (PersonNotFoundException e) {
                return new PrintMessageCommand("Person [" + input.trim() + "] not found");
            }
        }
    }

    private static ArrayList<String> handleTagsInputUI(Scanner scanner) {
        ArrayList<String> output = new ArrayList<>();
        while (true) {
            System.out.print("Key in a tag (Key in \"N/A\" to finish):\n> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("N/A")) {
                break;
            }
            if (input.isBlank()) {
                IOHandler.writeOutputWithColour("You cannot enter an empty tag.", RED);
            } else {
                output.add(input);
                System.out.println("Added tag: [" + input + "]");
            }
        }
        return output;
    }

    private static LoanCommand handleHelpCommand(String input) {
        if (input == null) {
            return new LoanHelpCommand();
        }
        switch (input.trim()) {
        case "find":
            return new PrintMessageCommand("""
                    You can find loanManager by entering these commands:
                    "find overdue loan": shows all overdue loanManager.
                    "find outgoing loan": shows all loanManager lent by you.
                    "find incoming loan": shows all loanManager borrowed by you.
                    "find my loan": shows all loanManager lent or borrowed by you.
                    "find [name] outgoing loan": shows all loanManager lent by [name].
                    "find [name] incoming loan": shows all loanManager borrowed by [name].
                    "find [name]": shows all loanManager lent or borrowed by [name].
                    "find [tag]": shows all loanManager tagged with [tag].
                    """);
        case "edit":
            return new PrintMessageCommand("""
                    You can edit the following attributes of the Xth loan by entering the "edit X [attribute]" command:
                    description
                    start date
                    return date
                    amount
                    principal
                    tag
                    interest (For advanced bullet loanManager only)
                    """);
        case "add":
            return new PrintMessageCommand("You can simply type \"add\", and more instructions will guide you.");
        default:
            return null;
        }
    }


}
