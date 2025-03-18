package utils.money;

public class CurrencyNotMatchException extends RuntimeException {
    public CurrencyNotMatchException(String message) {
        super(message);
    }
}
