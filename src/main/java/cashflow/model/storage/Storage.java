package cashflow.model.storage;

import cashflow.model.interfaces.Finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
    private File file;

    public Storage(String filePath) {
        file = new File(filePath);
    }

    /**
     * Loads the task data from the specified file location. If the file does not exist,
     * a FileNotFoundException is thrown.
     *
     * @return An ArrayList of objects loaded from storage.
     * @throws FileNotFoundException if the task data file does not exist.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Finance> loadFile() throws FileNotFoundException {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Storage loaded successfully");
            return (ArrayList<Finance>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return new ArrayList<Finance>();
    }

    /**
     * Saves the current list of Finance objects to disk.
     */
    public void saveFile(ArrayList<Finance> financeList) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(financeList);
            System.out.println("Tasks saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}

