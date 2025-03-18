package loanbook.save;

import loanbook.loan.Loan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the saving of loan information to a text file.
 */
public class LoanSaveManager {
    public static final String DEFAULT_PATH = "save/loansData.txt";
    public static final String SAVE_SEPARATOR = "<saveSeparator>";

    /**
     * Writes a text file at <code>filePath</code> with the contents of <code>text</code>.
     * Creates the directory if it does not exist
     * @param filePath the path where the file is stored.
     * @param text the content of the text file to be written. If null, creates a new empty file.
     * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but
     *     cannot be created, or cannot be opened for any other reason
     */
    public static void writeTextFile(String filePath, String text) throws IOException {
        Files.createDirectories(Paths.get("save"));
        if (text == null){
            new FileWriter(filePath, false).close();
        } else {
            FileWriter fw = new FileWriter(filePath);
            fw.write(text);
            fw.close();
        }
    }

    /**
     * Appends <code>appendText</code> to the file at <code>filePath</code>.
     * @param filePath the path of the file.
     * @param appendText the content to be appended.
     * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but
     *     cannot be created, or cannot be opened for any other reason
     */
    public static void appendTextFile(String filePath, String appendText) throws IOException {
        Files.createDirectories(Paths.get("save"));
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(appendText + '\n');
        fw.close();
    }

    /**
     * Reads the saved loans data from the file at <code>filePath</code>. Ignores corrupted lines.
     * @param filePath the path of the file.
     * @return an ArrayList of <code>Loan</code>.
     * @throws FileNotFoundException when the file is not found.
     */
    public static ArrayList<Loan> readSave(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        ArrayList<Loan> list = new ArrayList<>();
        Scanner s = new Scanner(f);
        while (s.hasNext()){
            list.add(readLoan(s.nextLine()));
        }
        return list;
    }

    /**
     * Reads one line of text and parses out each information of the loan.
     * @param line the line of text to be read.
     * @return a subclass of <code>Loan</code> based on the information of the text.
     */
    private static Loan readLoan(String line) {
        return null;
    }

}
