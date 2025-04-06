package budgetsaving.saving.exceptions;

public class SavingRuntimeException extends SavingException {
    private String detailMessage;

    // Since the attribute class is a subclass of parser, we can say its a parsing error
    public SavingRuntimeException(String message) {
        detailMessage = "Error in running your command: " + message;
    }

    public String getMessage(){
        return detailMessage;
    }

}
