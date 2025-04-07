package cashflow.analytics.utils;

import java.time.YearMonth;
import java.util.Map;

public class SpendingHelper {
    public static void printChart(int month, int year, double grandTotal, Map<String, Double> categoryTotals){
        System.out.println("Spending Breakdown for " + YearMonth.of(year, month) + ":");
        System.out.println("--------------------------------------------------");

        double maxValue = categoryTotals.values().stream()
                .mapToDouble(Double::doubleValue).max().orElse(0.0);

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double total = entry.getValue();
            double percent = (total / grandTotal) * 100.0;

            // Build the bar scaled to e.g. 50 characters wide
            int barLength = maxValue > 0 ? (int)((total / maxValue) * 50) : 0;
            String bar = new String(new char[barLength]).replace('\0', '#');

            // Print line: Category | ### (amount) XX%
            System.out.printf("%-15s | %-50s (%.2f) [%.1f%%]\n", category, bar, total, percent);
        }

        System.out.printf("Grand Total: %.2f\n", grandTotal);
        System.out.println("--------------------------------------------------");
    }
}
