package loanbook.save;

import loanbook.LoanManager;
import loanbook.interest.Interest;
import loanbook.loan.AdvancedBulletLoan;
import loanbook.loan.DateUndefinedException;
import loanbook.loan.Loan;
import loanbook.loan.SimpleBulletLoan;
import loanbook.parsers.InterestParser;
import utils.money.Money;
import utils.money.MoneyParser;
import utils.contacts.ContactsList;
import utils.contacts.ContactsSaveManager;
import utils.contacts.Person;
import utils.savemanager.SaveManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Old class that manages the saving of loan information to a text file. Use cashflow.model.storage.Storage instead.
 */
public class LoanSaveManager extends SaveManager {
    public static final String LOAN_SEPARATOR = "<LoanSaveSeparator>\n";
    public static final String LOANS_SAVE_FOLDER = "save/loanManager";
    public static final String SAVE_FILE_SUFFIX = "_loans.txt";

    /**
     * Separates the full <code>String</code> read from the save file into small <code>String</code>s, each containing
     *     information of one <code>Loan</code>. Reads each small <code>String</code> by calling the
     *     <code>readLoan()</code> method.
     * @param save the fill save String.
     * @return an <code>ArrayList</code> of <code>Loan</code>s.
     */
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

    /**
     * Parses every information of a <code>Loan</code> from a save String segment.
     * @param save the <code>String</code> that stores every information of one <code>Loan</code>.
     * @return a <code>Loan</code> class containing the parsed information.
     */
    private static Loan readLoan(String save) {
        String[] splitLine = save.split("\n");
        Person lender = new Person(splitLine[1].replace("<Lender>", "").trim());
        Person borrower = new Person(splitLine[2].replace("<Borrower>", "").trim());
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
        } else if (save.startsWith("<AdvancedBulletLoanStart>")) {
            Interest interest = InterestParser.parseInterest(splitLine[9].replace("<Interest>", "")
                    .trim());
            Loan loan;
            try {
                loan = new AdvancedBulletLoan(description, lender, borrower, principal, startDate, returnDate,
                        interest);
            } catch (DateUndefinedException e) {
                return null;
            }
            loan.setReturnStatus(isReturned);
            loan.addTags(tagList);
            return loan;
        }
        return null;
    }

    /**
     * Saves the <code>LoanManager</code> as a text file to the designated directory. The file name depends on the
     *     <code>user</code> of the LoanManager. If the LoanManager does not have a username, it is not stored.
     * @param loanManager the <code>LoanManager</code> that contains a list of recorded <code>Loan</code>s.
     * @throws IOException when the named file exists but is a directory rather than a regular file, does not exist but
     *     cannot be created, or cannot be opened for any other reason
     */
    public static void saveLoanList(LoanManager loanManager) throws IOException {
        if (loanManager.getUsername() != null) {
            ContactsSaveManager.savePeopleList(loanManager.getContactsList());
            writeTextFile(LOANS_SAVE_FOLDER, savePath(loanManager.getUsername()), loanManager.toSave());
        }
    }

    public static LoanManager readLoanList(String user) throws FileNotFoundException {
        String save = getSaveString(savePath(user));
        return new LoanManager(user, readSaveString(save));
    }

    public static void appendSave(String user, String text) throws IOException {
        appendTextFile(LOANS_SAVE_FOLDER, savePath(user), text);
    }

    private static String savePath(String user) {
        return LOANS_SAVE_FOLDER + '/' + user + SAVE_FILE_SUFFIX;
    }
}
