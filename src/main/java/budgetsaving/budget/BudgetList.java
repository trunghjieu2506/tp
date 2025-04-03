package budgetsaving.budget;

import budgetsaving.budget.utils.BudgetActiveStatus;
import budgetsaving.budget.utils.BudgetAlert;
import cashflow.model.interfaces.BudgetDataManager;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;
import utils.money.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

public class BudgetList implements BudgetManager, BudgetDataManager {
    private ArrayList<Budget> budgets;
    private Currency currency;
    private HashMap<String, Budget> budgetByCategory;

    // Modified constructor to accept a currency
    public BudgetList(Currency currency) {
        assert currency != null : "Currency must not be null or empty.";
        this.currency = currency;
        budgets = new ArrayList<>();
        budgetByCategory = new HashMap<>();
    }

    // Added getter (and optionally setter) for currency
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    //update the budget completion/active status
    //refresh the status constantly when the user starts the app
    private void updateBudgetCompletionStatus(Budget budget) {
        if (budget == null) {
            System.out.println("Budget does not exist.");
            return;
        }
        LocalDate currDate = LocalDate.now();
        LocalDate budgetExpireDate = budget.getEndDate();
        if (currDate.isAfter(budgetExpireDate)){
            budget.updateBudgetActiveStatus(BudgetActiveStatus.EXPIRED);
        }
    }

    //please call this method when initialising the app
    public void refreshBudgetStatuses(){
        for (int i = 0; i < budgets.size(); i++) {
            updateBudgetCompletionStatus(budgets.get(i));
        }
        System.out.println("Budget statuses are refreshed!");
    }


    public void addNewBudget(Budget budget) throws BudgetException {
        if (budget == null) {
            throw new BudgetException("Cannot add a null budget.");
        }
        String category = budget.getCategory();
        if (budgetByCategory.containsKey(category)) {
            throw new BudgetException("Budget in category " + category + " already exists.");
        }
        assert !budgets.contains(budget) : "Budget already exists before addition.";
        budgets.add(budget);
        budgetByCategory.put(category, budget);
        assert budgets.contains(budget) : "Budget not added properly.";
    }


    public void setBudget(String name, double amount, LocalDate endDate, String category) {
        Money money = new Money(currency, BigDecimal.valueOf(amount));
        Budget newBudget = new Budget(name, money, endDate, category);
        int initialSize = budgets.size();
        try {
            addNewBudget(newBudget);
            System.out.println("New budget added: " + newBudget);
        } catch (BudgetException e) {
            System.err.println("Error adding new budget: " + e.getMessage());
        }
        assert budgets.size() == initialSize + 1 :
                "Budget list size did not increase.";
        assert budgets.get(budgets.size() - 1).equals(newBudget) :
                "Last budget is not the newly added one.";
        assert budgetByCategory.get(newBudget.getCategory()).equals(newBudget) :
                "Budget hash mapping not updated properly.";
    }


    @Override
    public void listBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("No budgets available.");
        } else {
            System.out.println("Budget list:");
            for (int i = 0; i < budgets.size(); i++) {
                Budget b = budgets.get(i);
                System.out.println("Budget " + (i + 1) + ". " + b.toString());
            }
        }
    }

    @Override
    public void deductFromBudget(int index, double amount) {
        if (index < 0 || index >= budgets.size()) {
            System.out.println("Budget index out of range.");
            return;
        }
        Budget b = budgets.get(index);
        Money before = b.getRemainingBudget(); // assuming this exists
        b.deduct(amount);
        Money after = b.getRemainingBudget();
        assert after.getAmount().compareTo(before.getAmount()) <= 0 : "Budget did not decrease after deduction.";
        System.out.println("Budget deducted.");
        if (after.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            BudgetAlert.exceedBudgetAlert();
        }
        System.out.println(b);
    }

    public boolean deductBudgetFromExpense(Expense expense) {
        String category = expense.getCategory();
        //dont need to check if category is null
        //its done on expense side
        Budget targetBudget = null;
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getCategory().equals(category)) {
                targetBudget = budgets.get(i);
            }
        }
        boolean exceedBudget = false;
        try{
            exceedBudget = targetBudget.deductFromExpense(expense);
        } catch (NullPointerException e) {
            System.out.println("No budgets found.");
        }
        return exceedBudget;
    }

    public void addToBudget(int index, double amount) {
        if (index < 0 || index >= budgets.size()) {
            System.out.println("Budget index out of range.");
            return;
        }
        Budget b = budgets.get(index);
        Money before = b.getRemainingBudget(); // assuming getRemaining returns a Money object
        b.add(amount);
        Money after = b.getRemainingBudget();
        assert after.getAmount().compareTo(before.getAmount()) >= 0 : "Budget did not increase after addition.";
        System.out.println("Budget added");
        System.out.println(b.toString());
    }

    public Budget getBudget(int index) {
        if (index < 0 || index >= budgets.size()) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        return budgets.get(index);
    }

    public void modifyBudget(int index, String name, double amount, LocalDate endDate, String category)
            throws BudgetException {
        if (index < 0 || index >= budgets.size()) {
            throw new BudgetException("Index out of range.");
        }
        Budget b = budgets.get(index);
        String oldCategory = b.getCategory();
        if (!oldCategory.equals(category)) {
            if (budgetByCategory.containsKey(category)) {
                throw new BudgetException("Budget in category " + category + " already exists.");
            }
            b.modifyBudget(amount, name, endDate, category);
            budgetByCategory.remove(oldCategory);
            budgetByCategory.put(category, b);
        } else {
            b.modifyBudget(amount, name, endDate, category);
        }
    }

    //to list out all the expenses within the budget
    //incorporate it in command
    public void checkBudget(int index) {
        if (index < 0 || index >= budgets.size()) {
            System.out.println("Index out of range.");
            return;
        }
        System.out.println(budgets.get(index).printExpenses());
    }

    @Override
    public ArrayList<Finance> getBudgetList() {
        return new ArrayList<>(budgets);
    }

}
