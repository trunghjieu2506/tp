package cashflow.model.interfaces;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Finance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public abstract LocalDate getDate();
    public abstract double getAmount();
    public abstract String getType(); //return the type of class "income", "expense", "loan", etc
}
