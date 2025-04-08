package budgettest;

import cashflow.model.interfaces.Finance;
import cashflow.model.storage.Storage;

import java.util.ArrayList;

public class BudgetStorageDummy extends Storage {

    private ArrayList<Finance> savedFile = new ArrayList<>();

    public BudgetStorageDummy(String fileName) {
        super(fileName);
    }

    @Override
    public ArrayList<Finance> loadFile() {
        // Return an empty list for testing purposes.
        return new ArrayList<>();
    }

    @Override
    public void saveFile(ArrayList<Finance> financeList) {
        // Simply store the list so it can later be verified if needed.
        this.savedFile = new ArrayList<>(financeList);
    }

    public ArrayList<Finance> getSavedFile() {
        return savedFile;
    }
}

