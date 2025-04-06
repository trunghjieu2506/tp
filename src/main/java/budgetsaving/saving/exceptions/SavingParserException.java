package budgetsaving.saving.exceptions;

public class SavingParserException extends SavingException {
    private String detailMessage;

    // Since the attribute class is a subclass of parser, we can say its a parsing error
    public SavingParserException(String message) {
        detailMessage = "Error in parsing your command: " + message;
    }

    public String getMessage(){
        return detailMessage;
    }
}
