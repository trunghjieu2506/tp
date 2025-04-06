package budgetsaving.saving.exceptions;

import utils.io.IOHandler;

public class SavingException extends Exception {

    public SavingException() {

    }

    public SavingException(String message) {
        IOHandler.writeError(message);
    }

    public static void writeException(SavingException e){
        IOHandler.writeError(e.getMessage());
        IOHandler.writeError("Type 'help' to see all commands.");
    }
}
