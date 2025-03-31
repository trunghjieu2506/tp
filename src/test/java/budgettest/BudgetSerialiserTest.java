package budgettest;

import budgetsaving.budget.Budget;
import budgetsaving.budget.utils.BudgetSerialiser;
import org.junit.jupiter.api.Test;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetSerialiserTest {

    @Test
    public void testSerialiseAndDeserialiseBudget() {
        // Step 1: Create a sample budget
        String name = "Groceries";
        String currency = "SGD";
        BigDecimal amount = BigDecimal.valueOf(250.50);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        String category = "Food";

        Money total = new Money(currency, amount);
        Budget original = new Budget(name, total, endDate, category);

        // Step 2: Serialise
        String serialised = BudgetSerialiser.serialise(original);
        System.out.println("Serialised string: " + serialised);

        // Step 3: Deserialise
        Budget restored = BudgetSerialiser.deserialise(serialised);

        // Step 4: Assert that the deserialised budget matches the original
        assertEquals(original.getName(), restored.getName(), "Name should match");
        assertEquals(original.getCategory(), restored.getCategory(), "Category should match");
        assertEquals(original.getEndDate(), restored.getEndDate(), "End date should match");
        assertEquals(original.getRemainingBudget().getCurrency(), restored.getRemainingBudget().getCurrency(), "Currency should match");
        assertEquals(
                original.getRemainingBudget().getAmount().doubleValue(),
                restored.getRemainingBudget().getAmount().doubleValue(),
                0.001,
                "Remaining budget amount should match"
        );
    }
}
