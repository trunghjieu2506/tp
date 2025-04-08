package cashflow.analytics.utils;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InsightHelper {
    private static void printNewSpending(String category, double currentAmount) {
        System.out.printf("- New spending in category '%s': %.2f (no spending in this category last month)\n",
                category, currentAmount);
    }

    private static void printChange(String category, double currentAmount,
                                    double lastAmount, double percentChange) {
        if (percentChange > 0) {
            System.out.printf("- You spent %.1f%% more on %s this month than last month. (%.2f vs. %.2f)\n",
                    percentChange, category, currentAmount, lastAmount);
        } else {
            System.out.printf("- You spent %.1f%% less on %s this month than last month. (%.2f vs. %.2f)\n",
                    Math.abs(percentChange), category, currentAmount, lastAmount);
        }
    }

    public static void displayInsights(YearMonth current, YearMonth last, Map<String,
            Double> currentTotals, Map<String, Double> lastTotals){
        Set<String> allCategories = new HashSet<>();
        allCategories.addAll(currentTotals.keySet());
        allCategories.addAll(lastTotals.keySet());

        System.out.println("Spending Insights for " + current + " vs. " + last + ":");
        System.out.println("--------------------------------------------------");

        boolean foundSignificantChange = false;

        for (String category : allCategories) {
            double currentAmount = currentTotals.getOrDefault(category, 0.0);
            double lastAmount = lastTotals.getOrDefault(category, 0.0);

            if (lastAmount <= 0) {
                // If lastAmount == 0 but currentAmount > 0 => new spending
                if (lastAmount == 0 && currentAmount > 0) {
                    printNewSpending(category, currentAmount);
                }
                continue;
            }

            // Now we know lastAmount > 0, so we can calculate the change
            double diff = currentAmount - lastAmount;
            double percentChange = (diff / lastAmount) * 100.0;

            // Skip minor change (< 20%)
            if (Math.abs(percentChange) < 20.0) {
                continue;
            }

            foundSignificantChange = true;
            printChange(category, currentAmount, lastAmount, percentChange);
        }

        if (!foundSignificantChange) {
            System.out.println("- No significant changes detected in spending categories.");
        }
        System.out.println("--------------------------------------------------");
    }
}
