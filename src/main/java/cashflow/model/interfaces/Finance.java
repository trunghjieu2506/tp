package cashflow.model.interfaces;
import utils.money.Money;

import java.time.LocalDate;

public abstract class Finance {
    public abstract LocalDate getDate();
    public abstract Money getAmount();
    public abstract String getType(); //return the type of class "income", "expense", "loan", etc
}
