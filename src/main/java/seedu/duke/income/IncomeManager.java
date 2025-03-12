package seedu.duke.income;

import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private List<Income> incomes;

    public IncomeManager() {
        this.incomes = new ArrayList<>();
    }

    public void addIncome(String source, double amount) {
        Income income = new Income(source, amount);
        incomes.add(income);
        System.out.println("Added: " + income);
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
        if (index >= 1 && index <= incomes.size()) {
            Income removed = incomes.remove(index - 1);
            System.out.println("Deleted: " + removed);
        } else {
            System.out.println("Invalid income number.");
        }
    }
}
