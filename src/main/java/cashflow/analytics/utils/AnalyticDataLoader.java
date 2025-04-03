package cashflow.analytics.utils;

import cashflow.model.FinanceData;
import cashflow.model.interfaces.Finance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyticDataLoader {
    private FinanceData data;
    public AnalyticDataLoader(FinanceData data) {
        this.data = data;
    }

    /**
     * Helper method to get total income for a given month/year.
     * This is used for the "comparison with last month" logic.
     */
    public double getTotalIncomeForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("income".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to get total expenses for a given month/year.
     */
    public double getTotalExpensesForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("expense".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getNetSavingsForMonth(int month, int year) {
        return getTotalIncomeForMonth(month, year) - getTotalExpensesForMonth(month, year);
    }

    public double getTotalLoanForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("loan".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getTotalDebtForMonth(int month, int year) {
        List<Finance> tx = retrieveTransactionsForMonth(month, year);
        double total = 0;
        for (Finance t : tx) {
            if ("loan".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Helper method to retrieve transactions for a given month/year
     * from all relevant managers.
     */
    public ArrayList<Finance> retrieveTransactionsForMonth(int month, int year) {
        ArrayList<Finance> allTransactions = retrieveAllTransactions();
        // Filter by year/month
        return allTransactions.stream()
                .filter(t -> t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Finance> retrieveTransactionsFromRange(LocalDate start, LocalDate end) {
        ArrayList<Finance> allTransactions = retrieveAllTransactions();
        // Filter by year/month
        return allTransactions.stream()
                .filter(t -> t.getDate().isAfter(start) && t.getDate().isBefore(end))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Finance> retrieveAllTransactions(){
        ArrayList<Finance> expenseList = data.getExpenseManager() != null
                ? data.getExpenseManager().getExpenseList()
                : new ArrayList<Finance>();

        ArrayList<Finance> incomeList = data.getIncomeManager() != null
                ? data.getIncomeManager().getIncomeList()
                : new ArrayList<Finance>();
//        ArrayList<Finance> savingsList = data.getSavingsManager() != null
//                ? data.getSavingsManager().getSavingList()
//                : new ArrayList<Finance>();

        ArrayList<Finance> loanDebtList = data.getLoanManager() != null
                ? data.getLoanManager().getLoanList()
                : new ArrayList<Finance>();

        // Combine them
        ArrayList<Finance> allTransactions = new ArrayList<>();
        allTransactions.addAll(expenseList);
        allTransactions.addAll(incomeList);
//        allTransactions.addAll(savingsList);
        allTransactions.addAll(loanDebtList);
        return allTransactions;
    }
}
