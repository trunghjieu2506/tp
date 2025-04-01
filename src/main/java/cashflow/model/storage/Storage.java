package cashflow.model.storage;

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

    /**
     * Initializes the Storage object with the specified file path for data persistence.
     *
     * @param filePath the file path used to save and load task data.
     */
    public Storage(String filePath) {
        file = new File(filePath);
    }

    /**
     * Loads the task data from the specified file location. If the file does not exist,
     * a FileNotFoundException is thrown.
     *
     * @return An ArrayList of tasks loaded from storage.
     * @throws FileNotFoundException if the task data file does not exist.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> loadFile() throws FileNotFoundException {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Storage loaded successfully");
            return (ArrayList<Object>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
        return new ArrayList<Object>();
    }

    /**
     * Saves the current list of tasks to disk.
     *
     * @param taskList the list of tasks to save to file.
     */
    public void saveFile(ArrayList<Object> taskList) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(taskList);
            System.out.println("Tasks saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}

