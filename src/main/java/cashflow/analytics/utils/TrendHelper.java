package cashflow.analytics.utils;

import cashflow.model.interfaces.Finance;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendHelper {
    public static List<Map.Entry<String, Double>> processWeeklyTrend(List<Finance> allTransactions, LocalDate start, LocalDate end) {
        Map<String, Double> transactionSums = new HashMap<>();
        for (Finance t : allTransactions) {
            String key = getWeekGroupKey(t.getDate()); // e.g., "2025-W13"
            transactionSums.put(key, transactionSums.getOrDefault(key, 0.0) + t.getAmount());
        }

        // This is to create entries for weeks without any transactions
        Map<String, Double> groupedData = new HashMap<>();
        LocalDate weekNumber = start;
        while (!weekNumber.isAfter(end)) {
            String weekKey = getWeekGroupKey(weekNumber);
            double sum = transactionSums.getOrDefault(weekKey, 0.0);
            groupedData.put(weekKey, sum);
            weekNumber = weekNumber.plusWeeks(1);
        }
        // Sort ascending by year/week
        List<Map.Entry<String, Double>> sortedWeekly = new ArrayList<>(groupedData.entrySet());
        sortedWeekly.sort((a, b) -> compareWeekKeys(a.getKey(), b.getKey()));
        return sortedWeekly;
    }

    public static List<Map.Entry<String, Double>> processMonthlyTrend(List<Finance> allTransactions, LocalDate start, LocalDate end){
        Map<String, Double> monthlySums = new HashMap<>();
        for (Finance t : allTransactions) {
            YearMonth ym = YearMonth.of(t.getDate().getYear(), t.getDate().getMonthValue());
            String groupKey = ym.toString(); // e.g. "2025-04"
            monthlySums.put(groupKey, monthlySums.getOrDefault(groupKey, 0.0) + t.getAmount());
        }

        // This is to create entries for months without any transactions
        Map<String, Double> groupedData = new HashMap<>();
        YearMonth monthNumber = YearMonth.of(start.getYear(), start.getMonthValue());
        YearMonth endYM = YearMonth.of(end.getYear(), end.getMonthValue());

        while (!monthNumber.isAfter(endYM)) {
            String groupKey = monthNumber.toString();
            double sum = monthlySums.getOrDefault(groupKey, 0.0);
            groupedData.put(groupKey, sum);
            monthNumber = monthNumber.plusMonths(1);
        }

        // Sort entries by ascending "YYYY-MM"
        List<Map.Entry<String, Double>> sortedMonthly = new ArrayList<>(groupedData.entrySet());
        sortedMonthly.sort((a, b) -> compareMonthKeys(a.getKey(), b.getKey()));
        return sortedMonthly;
    }


    /**
     * Helper method: prints the ASCII bar chart.
     */
    public static void printTrendChart(List<Map.Entry<String, Double>> sortedEntries,
                                 String dataType, LocalDate start, LocalDate end,
                                 String interval) {

        System.out.println("Trend Over Time for " + dataType.toUpperCase() + " (" + interval + "):");
        System.out.println("From " + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                " to " + end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("--------------------------------------------");

        double maxValue = sortedEntries.stream().mapToDouble(Map.Entry::getValue).max().orElse(0.0);
        for (Map.Entry<String, Double> entry : sortedEntries) {
            String label = entry.getKey();
            double amount = entry.getValue();
            int barLength = maxValue > 0 ? (int)((amount / maxValue) * 50) : 0;
            String bar = new String(new char[barLength]).replace('\0', '#');
            System.out.printf("%-10s | %s (%.2f)\n", label, bar, amount);
        }
        System.out.println("--------------------------------------------");
    }

    /**
     * Helper method to create a "YYYY-W##" key for weekly grouping.
     */
    private static String getWeekGroupKey(LocalDate date) {
        int year = date.getYear();
        // For the ISO week number:
        int weekOfYear = date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return year + "-W" + weekOfYear;
    }

    /**
     * Compare two "YYYY-W##" strings in ascending order (by year, then week).
     */
    private static int compareWeekKeys(String w1, String w2) {
        // e.g., w1 = "2025-W13"
        String[] p1 = w1.split("-W");
        String[] p2 = w2.split("-W");

        int year1 = Integer.parseInt(p1[0]);
        int year2 = Integer.parseInt(p2[0]);
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        int week1 = Integer.parseInt(p1[1]);
        int week2 = Integer.parseInt(p2[1]);
        return Integer.compare(week1, week2);
    }

    /**
     * Compare two "YYYY-MM" strings in ascending order (by year, then month).
     */
    private static int compareMonthKeys(String m1, String m2) {
        // e.g., m1 = "2025-04"
        String[] p1 = m1.split("-");
        String[] p2 = m2.split("-");

        int year1 = Integer.parseInt(p1[0]);
        int year2 = Integer.parseInt(p2[0]);
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        int month1 = Integer.parseInt(p1[1]);
        int month2 = Integer.parseInt(p2[1]);
        return Integer.compare(month1, month2);
    }
}
