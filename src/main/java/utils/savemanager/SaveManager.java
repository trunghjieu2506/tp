package utils.savemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SaveManager {
    public static final String SAVE_FOLDER = "save";

    protected static String getSaveString(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner scanner = new Scanner(f);
        StringBuilder save = new StringBuilder();
        while (scanner.hasNext()) {
            save.append(scanner.nextLine()).append('\n');
        }
        return save.toString();
    }

    /**
     * Writes a text file at <code>filePath</code> with the contents of <code>text</code>.
     * Creates the directory if it does not exist
     * @param filePath the path where the file is stored.
     * @param text the content of the text file to be written. If null, creates a new empty file.
     * @throws IOException if the named file exists but is a directory rather than a regular file, does not exist but
     *     cannot be created, or cannot be opened for any other reason
     */
    public static void writeTextFile(String fileFolder, String filePath, String text) throws IOException {
        Files.createDirectories(Paths.get(fileFolder));
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
    public static void appendTextFile(String fileFolder, String filePath, String appendText) throws IOException {
        Files.createDirectories(Paths.get(fileFolder));
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(appendText + '\n');
        fw.close();
    }
}
