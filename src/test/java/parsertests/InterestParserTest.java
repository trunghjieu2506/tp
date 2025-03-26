package parsertests;

import loanbook.loanbook.interest.Interest;
import loanbook.loanbook.interest.InterestType;
import loanbook.loanbook.parsers.InterestParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.Period;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterestParserTest {
    @Test
    public void testOne() {
        String input = """
                compound
                COMPOUND 5% per 2 years
                """;
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Interest interest = InterestParser.handleInterestInputUI(scanner);
        Interest expected = new Interest(5, InterestType.COMPOUND, Period.ofYears(2));
        System.out.println("Input interest: " + interest.toString());
        assertEquals(expected.toString(), interest.toString());
    }
}
