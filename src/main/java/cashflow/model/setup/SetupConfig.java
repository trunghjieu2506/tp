package cashflow.model.setup;

import java.io.Serializable;

/**
 * A simple serializable object for storing first-time usage flag
 * and the chosen currency code (e.g., "USD", "SGD", etc.)
 */
public class SetupConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean firstTime;
    private String currencyCode;  // e.g. "USD"
    private String username;

    public SetupConfig(boolean firstTime, String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public SetupConfig(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public SetupConfig(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public boolean isFirstTime() {
        return firstTime;
    }
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }
}