package cashflow.model.storage;

import cashflow.model.interfaces.Finance;
import cashflow.model.setup.SetupConfig;

import java.util.ArrayList;

/**
 * Dummy storage that stores finance data in memory instead of disk.
 */
public class DummyStorage extends Storage {

    private ArrayList<Finance> memoryData;

    public DummyStorage() {
        super("dummy/path/does/not/matter.txt");
        this.memoryData = new ArrayList<>();
    }

    @Override
    public void saveFile(ArrayList<Finance> financeList) {
        memoryData = new ArrayList<>(financeList);
    }

    @Override
    public ArrayList<Finance> loadFile() {
        return new ArrayList<>(memoryData);
    }

    @Override
    public void saveSetupConfig(SetupConfig config) {
    }

    @Override
    public SetupConfig loadSetupConfig() {
        return null;
    }
}
