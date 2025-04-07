package utils.datetime;

import utils.io.IOHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static utils.textcolour.TextColour.RED;

/**
 * Contains a method to parse the date of a given input.
 */
public class DateParser {
    /**
     * Parses the date of a given input.
     * @param input the String where the date is parsed.
     * @return a <code>LocalDate</code> class.
     * @throws DateTimeParseException when no dates can be parsed.
     */
    public static LocalDate parse(String input) throws DateTimeParseException {
        String processed = input.replaceAll("\\D", " ").trim().replaceAll(" +", "-");
        String[] split = processed.split("-", 3);
        try {
            if (split[1].length() == 1) {
                split[1] = "0" + split[1];
            }
            if (split[2].length() == 1) {
                split[2] = "0" + split[2];
            }
            String joint = String.join("-", split);
            return LocalDate.parse(joint);
        } catch (ArrayIndexOutOfBoundsException e) {
            return LocalDate.parse(processed);
        }
    }

    public static LocalDate handleLocalDateUI(Scanner scanner, String instruction) {
        LocalDate date;
        while (true) {
            System.out.print(instruction + "\n> ");
            String input = scanner.nextLine();
            try {
                date = parse(input);
                break;
            } catch (DateTimeParseException e) {
                IOHandler.writeOutputWithColour("Invalid date format!", RED);
            }
        }
        return date;
    }

    public static LocalDate handleLocalDateUI(Scanner scanner, String instruction, boolean allowNull) {
        if (!allowNull) {
            return handleLocalDateUI(scanner, instruction);
        }
        LocalDate date;
        while (true) {
            System.out.print(instruction + " (Key in \"N/A\" if not applicable):" + "\n> ");
            String input = scanner.nextLine().trim();
            try {
                if (input.equals("N/A")) {
                    return null;
                }
                date = parse(input);
                break;
            } catch (DateTimeParseException e) {
                IOHandler.writeOutputWithColour("Invalid date format!", RED);
            }
        }
        return date;
    }

    public static LocalDate handleReturnDateUI(Scanner scanner, String instruction, LocalDate startDate) {
        LocalDate date;
        while (true) {
            System.out.print(instruction + "\n> ");
            String input = scanner.nextLine().trim();
            try {
                date = parse(input);
                if (date.isBefore(startDate)) {
                    IOHandler.writeOutputWithColour("The return date cannot be before the start date", RED);
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                IOHandler.writeOutputWithColour("Invalid date format!", RED);
            }
        }
        return date;
    }

    public static LocalDate handleReturnDateUI(Scanner scanner, String instruction, LocalDate startDate,
                                               boolean allowNull) {
        if (!allowNull) {
            return handleReturnDateUI(scanner, instruction, startDate);
        }
        LocalDate date;
        while (true) {
            System.out.print(instruction + " (Key in \"N/A\" if not applicable):" + "\n> ");
            String input = scanner.nextLine().trim();
            try {
                if (input.equals("N/A")) {
                    return null;
                }
                date = parse(input);
                if (date.isBefore(startDate)) {
                    IOHandler.writeOutputWithColour("The return date cannot be before the start date", RED);
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                IOHandler.writeOutputWithColour("Invalid date format!", RED);
            }
        }
        return date;
    }
}
