package loanbook.save;

import loanbook.LoanList;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedLoan;
import loanbook.loan.DateUndefinedException;
import loanbook.loan.Loan;
import loanbook.loan.SimpleBulletLoan;
import loanbook.parsers.InterestParser;
import utils.money.Money;
import utils.money.MoneyParser;
import utils.people.PeopleList;
import utils.people.Person;
import utils.savemanager.SaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages the saving of loan information to a text file.
 */
public class LoanSaveManager extends SaveManager {
    public static final String LOAN_SAVE_PATH = "save/loansData.txt";
    public static final String LOAN_SEPARATOR = "<LoanSaveSeparator>\n";

    public static ArrayList<Loan> readSaveString(String save) {
        String[] splitLoan = save.split(LOAN_SEPARATOR);
        ArrayList<Loan> list = new ArrayList<>();
        for (String loanString : splitLoan) {
            Loan loan = readLoan(loanString);
            if (loan != null) {
                list.add(loan);
            }
        }
        return list;
    }

    private static Loan readLoan(String save) throws IllegalArgumentException {
        String[] splitLine = save.split("\n");
        Person lender = PeopleList.findOrAddPerson(splitLine[1].replace("<Lender>", "").trim());
        Person borrower = PeopleList.findOrAddPerson(splitLine[2].replace("<Borrower>", "").trim());
        Money principal = MoneyParser.parse(splitLine[3].replace("<Principal>", "").trim());
        LocalDate startDate;
        LocalDate returnDate;
        try {
            startDate = LocalDate.parse(splitLine[4].replace("<StartDate>", "").trim());
        } catch (DateTimeParseException e) {
            startDate = null;
        }
        try {
            returnDate = LocalDate.parse(splitLine[5].replace("<ReturnDate>", "").trim());
        } catch (DateTimeParseException ignored) {
            returnDate = null;
        }
        String description = splitLine[6].replace("<Description>", "").trim();
        ArrayList<String> tagList = null;
        if (!splitLine[7].startsWith("<Tags>None")) {
            String[] tags = splitLine[7].replace("<Tags>", "").split(", ");
            tagList = new ArrayList<>(Arrays.asList(tags));
        }
        boolean isReturned = Boolean.parseBoolean(splitLine[8]);
        if (save.startsWith("<SimpleBulletLoanStart>")) {
            Loan loan = new SimpleBulletLoan(description, lender, borrower, principal, startDate, returnDate);
            loan.setReturnStatus(isReturned);
            loan.addTags(tagList);
            return loan;
        } else if (save.startsWith("<AdvancedLoanStart>")) {
            Interest interest = InterestParser.parseInterest(splitLine[9].replace("<Interest>", "").trim());
            Loan loan;
            try {
                loan = new AdvancedLoan(description, lender, borrower, principal, startDate, returnDate, interest);
            } catch (DateUndefinedException e) {
                return null;
            }
            loan.setReturnStatus(isReturned);
            loan.addTags(tagList);
            return loan;
        }
        return null;
    }

    public static void saveLoanList(LoanList loanList) throws IOException {
        writeTextFile(LOAN_SAVE_PATH, loanList.toSave());
    }

    public static LoanList readLoanList() throws FileNotFoundException {
        String save = getSaveString(LOAN_SAVE_PATH);
        return new LoanList(readSaveString(save));
    }

    public static void appendSave(String text) throws IOException {
        appendTextFile(LOAN_SAVE_PATH, text);
    }
}
