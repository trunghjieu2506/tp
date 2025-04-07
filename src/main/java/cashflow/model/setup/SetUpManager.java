package cashflow.model.setup;

import cashflow.model.storage.Storage;

public class SetUpManager {
    private Storage setupStorage;
    public SetUpManager(Storage setupStorage) {
        this.setupStorage = setupStorage;
    }

    public Storage getSetupStorage() {
        return setupStorage;
    }
}
