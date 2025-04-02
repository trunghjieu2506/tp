package cashflow.model.interfaces;
import java.time.LocalDate;

public abstract class Finance {
    public abstract LocalDate getDate();
    public abstract double getAmount();
    public abstract String getType(); //return the type of class "income", "expense", "loan", etc
}
