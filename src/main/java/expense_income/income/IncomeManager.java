package expense_income.income;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Comparator;

public class IncomeManager {
    private List<Income> incomes;

    public IncomeManager() {
        this.incomes = new ArrayList<>();
    }

    public void addIncome(String source, double amount, LocalDate date) {
        try {
            if (source == null || source.trim().isEmpty()) {
                throw new IllegalArgumentException("Income source cannot be empty.");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Income amount must be greater than zero.");
            }

            Income income = new Income(source, amount, date);
            incomes.add(income);
            System.out.println("Added: " + income);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add income. " + e.getMessage());
        }
    }

    public void listIncomes() {
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded.");
            return;
        }
        System.out.println("Income List:");
        for (int i = 0; i < incomes.size(); i++) {
            System.out.println((i + 1) + ". " + incomes.get(i));
        }
    }

    public void deleteIncome(int index) {
        try {
            if (index < 1 || index > incomes.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + incomes.size());
            }
            Income removed = incomes.remove(index - 1);
            System.out.println("Deleted: " + removed);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to delete income. " + e.getMessage());
        }
    }

    public void editIncome(int index, String newSource, double newAmount, LocalDate newDate) {
        try {
            if (index < 1 || index > incomes.size()) {
                throw new IllegalArgumentException("Invalid index: must be between 1 and " + incomes.size());
            }
            if (newSource == null || newSource.trim().isEmpty()) {
                throw new IllegalArgumentException("New source cannot be empty.");
            }
            if (newAmount <= 0) {
                throw new IllegalArgumentException("New amount must be greater than zero.");
            }

            Income income = incomes.get(index - 1);
            income.setSource(newSource);
            income.setAmount(newAmount);
            income.setDate(newDate);
            System.out.println("Updated: " + income);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to edit income. " + e.getMessage());
        }
    }

    public void sortIncomesByDate(boolean mostRecentFirst) {
        if (incomes.isEmpty()) {
            System.out.println("No incomes to sort.");
            return;
        }

        incomes.sort((i1, i2) -> {
            if (mostRecentFirst) {
                return i2.getDate().compareTo(i1.getDate());
            } else {
                return i1.getDate().compareTo(i2.getDate());
            }
        });

        System.out.println("Incomes sorted by " + (mostRecentFirst ? "most recent" : "oldest") + " first.");
        listIncomes();
    }

    public int getIncomeCount() {
        return incomes.size();
    }

    public Income getIncome(int i) {
        return incomes.get(i);
    }
}
