package budgetsaving.budget.utils;

import budgetsaving.budget.Budget;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetSerialiser {

    public static String serialise(Budget budget) {
        StringBuilder sb = new StringBuilder();

        sb.append(nullSafe(budget.getName())).append("|")
                .append(nullSafe(budget.getRemainingBudget().getCurrency())).append("|")
                .append(nullSafe(budget.getRemainingBudget().getAmount())).append("|")
                .append(nullSafe(budget.getCategory())).append("|")
                .append(nullSafe(budget.getEndDate())).append("|");

        // Add optional: totalBudget and startDate (inferred internally)
        sb.append(nullSafe(budget.getRemainingBudget().getAmount())).append("|")
                .append(nullSafe(LocalDate.now()));  // Optional: could save startDate if you make a getter

        return sb.toString();
    }

    public static Budget deserialise(String data) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Input data is empty.");
        }

        String[] parts = data.split("\\|", -1);  // Keep empty strings

        String name = parts.length > 0 ? parts[0] : "Unnamed Budget";
        String currency = parts.length > 1 && !parts[1].isEmpty() ? parts[1] : "SGD";
        BigDecimal remaining = parts.length > 2 && !parts[2].isEmpty()
                ? new BigDecimal(parts[2]) : BigDecimal.ZERO;
        String category = parts.length > 3 ? parts[3] : "Uncategorised";
        LocalDate endDate = parts.length > 4 && !parts[4].isEmpty()
                ? LocalDate.parse(parts[4]) : LocalDate.now().plusDays(1);

        // Create the Money object
        Money totalMoney = new Money(currency, remaining);

        // Construct the Budget object
        Budget budget = new Budget(name, totalMoney, endDate, category);

        // Set the remaining budget if it's not equal to total
        if (parts.length > 5 && !parts[5].isEmpty()) {
            BigDecimal remainingOverride = new BigDecimal(parts[5]);
            budget.setRemainingBudget(remainingOverride.doubleValue());
        }

        return budget;
    }

    private static String nullSafe(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
