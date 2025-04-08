package cashflow.model.storage;

import cashflow.model.interfaces.Finance;
import cashflow.model.setup.SetupConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The Storage class is responsible for handling the loading and saving
 * of application data to disk. It provides methods to read and write both
 * general {@link Finance} objects and the {@link SetupConfig}.
 */
public class Storage {
    private File file;

    public Storage(String filePath) {
        file = new File(filePath);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Finance> loadFile() throws FileNotFoundException {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Finance>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            return null;
        }
    }

    public void saveFile(ArrayList<Finance> financeList) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(financeList);
        } catch (IOException e) {
            System.err.println("Error saving File: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public SetupConfig loadSetupConfig() {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (SetupConfig) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading SetupConfig: " + e.getMessage());
            return null;
        }
    }

    public void saveSetupConfig(SetupConfig config) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(config);
        } catch (IOException e) {
            System.err.println("Error saving SetupConfig: " + e.getMessage());
        }
    }

}

