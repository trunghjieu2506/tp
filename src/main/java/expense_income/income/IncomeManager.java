package expense_income.income;

import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private List<Income> incomes;

    public IncomeManager() {
        this.incomes = new ArrayList<>();
    }

    public void addIncome(String source, double amount) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Income source cannot be empty.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Income amount must be greater than zero.");
        }

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
        if (index < 1 || index > incomes.size()) {
            throw new IllegalArgumentException("Invalid index: must be between 1 and " + incomes.size());
        }
        Income removed = incomes.remove(index - 1);
        System.out.println("Deleted: " + removed);
    }


    public int getIncomeCount() {
        return incomes.size();
    }

    public Income getIncome(int i) {
        return incomes.get(i);
    }
}
