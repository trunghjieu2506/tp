package utiltest.parsertests;

import org.junit.jupiter.api.Test;
import utils.datetime.DateParser;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateParserTest {
    @Test
    public void testOne() {
        String input = """
                not a date
                2025-13-13
                01-01-2025
                2025-1-1
                2025-03-26
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        LocalDate date = DateParser.handleLocalDateUI(scanner, "Enter the date");
        System.out.println("Date parsed is: " + date);
        LocalDate expected = LocalDate.of(2025, 1, 1);
        assertEquals(expected, date);
    }

    @Test
    public void testUI() {

    }
}
