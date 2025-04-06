package budgetsaving.saving.exceptions;

import utils.io.IOHandler;

public class SavingException extends Exception {

    public SavingException() {
        super();
    }

    public SavingException(String message) {
        IOHandler.writeError(message);
    }
}
