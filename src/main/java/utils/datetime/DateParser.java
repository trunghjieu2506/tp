package utils.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
}
