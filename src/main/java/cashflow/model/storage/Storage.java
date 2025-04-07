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
            System.out.println("Storage loaded successfully");
            return (ArrayList<Finance>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the current list of tasks to disk.
     *
     * @param financeList the list of tasks to save to file.
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

    @SuppressWarnings("unchecked")
    public SetupConfig loadSetupConfig() {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            System.out.println("No setup config file found at " + file.getPath());
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("SetupConfig loaded successfully");
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
            System.out.println("SetupConfig saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving SetupConfig: " + e.getMessage());
        }
    }

}

