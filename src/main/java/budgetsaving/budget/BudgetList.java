package budgetsaving.budget;

import budgetsaving.budget.exceptions.BudgetException;
import budgetsaving.budget.exceptions.BudgetRuntimeException;
import budgetsaving.budget.utils.BudgetActiveStatus;
import budgetsaving.budget.utils.BudgetAlert;
import cashflow.model.interfaces.BudgetDataManager;
import cashflow.model.interfaces.BudgetManager;
import cashflow.model.interfaces.Finance;
import cashflow.model.storage.Storage;
import expenseincome.expense.Expense;
import utils.io.IOHandler;
import utils.money.Money;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Optional;

public class BudgetList implements BudgetManager, BudgetDataManager {
    private ArrayList<Budget> budgets;
    private Currency currency;
    private HashMap<String, Budget> budgetByCategory;
    private Storage budgetStorage;

    public BudgetList(Currency currency) {
        assert currency != null : "Currency must not be null or empty.";
        this.currency = currency;
        budgets = new ArrayList<>();
        budgetByCategory = new HashMap<>();
    }

    // Modified constructor to accept a currency
    public BudgetList(Currency currency, Storage budgetStorage) throws FileNotFoundException {
        assert currency != null : "Currency must not be null or empty.";
        this.currency = currency;
        budgets = new ArrayList<>();
        budgetByCategory = new HashMap<>();
        this.budgetStorage = budgetStorage;
        ArrayList<Finance> loadedFile = budgetStorage.loadFile();
        if (loadedFile != null){
            for (Finance f : loadedFile) {
                if (f instanceof Budget) {
                    budgets.add((Budget) f);
                    budgetByCategory.put(capitalize(((Budget) f).getCategory()), (Budget)f);
                }
            }
        }
    }

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
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
            IOHandler.writeOutput("Budget does not exist.");
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
        IOHandler.writeOutput("Budget statuses are refreshed!");
    }


    public void addNewBudget(Budget budget) throws BudgetException {
        if (budget == null) {
            throw new BudgetException("Cannot add a null budget.");
        }
        String category = capitalize(budget.getCategory());
        if (budgetByCategory.containsKey(category)) {
            throw new BudgetRuntimeException("Budget in category '" + category + "' already exists.");
        }
        assert !budgets.contains(budget) : "Budget already exists before addition.";
        budgets.add(budget);
        budgetByCategory.put(capitalize(category), budget);
        assert budgets.contains(budget) : "Budget not added properly.";
    }


    public void setBudget(String name, double amount, LocalDate endDate, String category) {
        Money money = new Money(currency, BigDecimal.valueOf(amount));
        Budget newBudget = new Budget(name, money, endDate, category);
        int initialSize = budgets.size();
        try {
            addNewBudget(newBudget);
            budgetStorage.saveFile(new ArrayList<>(budgets));
            IOHandler.writeOutput("New budget added, type 'check i/INDEX' to check the details");
            assert budgets.size() == initialSize + 1 :
                    "Budget list size did not increase.";
            assert budgets.get(budgets.size() - 1).equals(newBudget) :
                    "Last budget is not the newly added one.";
            assert budgetByCategory.get(newBudget.getCategory()).equals(newBudget) :
                    "Budget hash mapping not updated properly.";
        } catch (BudgetException e) {
            IOHandler.writeError(e.getMessage());
        }
    }


    @Override
    public void listBudgets() {
        if (budgets.isEmpty()) {
            IOHandler.writeOutput("No budgets available.");
        } else {
            IOHandler.writeOutput("Budget list:");
            for (int i = 0; i < budgets.size(); i++) {
                Budget b = budgets.get(i);
                IOHandler.writeOutput("Budget " + (i + 1) + ". " + b.toString());
            }
        }
    }

    @Override
    public void deductFromBudget(int index, double amount) {
        if (index < 0 || index >= budgets.size()) {
            IOHandler.writeOutput("Budget index out of range.");
            return;
        }
        Budget b = budgets.get(index);
        Money before = b.getRemainingBudget(); // assuming this exists
        b.deduct(amount);
        Money after = b.getRemainingBudget();
        budgetStorage.saveFile(new ArrayList<>(budgets));
        assert after.getAmount().compareTo(before.getAmount()) <= 0 : "Budget did not decrease after deduction.";
        IOHandler.writeOutput("Budget deducted.");
        if (after.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            BudgetAlert.exceedBudgetAlert();
        }
        IOHandler.writeOutput(b.toString());
    }

    public boolean deductBudgetFromExpense(Expense expense) {
        Budget targetBudget = null;
        try {
            // Retrieve the budget for the given expense category. If none is found, an exception is thrown.
            targetBudget = getBudgetForCategory(expense.getCategory())
                    .orElseThrow(() -> new BudgetRuntimeException("Expense is not in any of the budget category"));

            checkExpenseDateWithinBudget(expense, targetBudget);

            boolean hasExceededBudget = targetBudget.deductFromExpense(expense);
            budgetStorage.saveFile(new ArrayList<>(budgets));
            IOHandler.writeOutput("Budget deducted: " + targetBudget);
            if (targetBudget.getRemainingBudget().getAmount().doubleValue() < 0) {
                return true;
            }
            return hasExceededBudget;
        } catch (BudgetRuntimeException e) {
            IOHandler.writeError(e.getMessage());
        }
        return false;
    }

    private Optional<Budget> getBudgetForCategory(String category) {
        return budgets.stream()
                .filter(budget -> budget.getCategory().equalsIgnoreCase(category))
                .findFirst();
    }

    private void checkExpenseDateWithinBudget(Expense expense, Budget budget) {
        if (expense.getDate().isBefore(budget.getStartDate()) || expense.getDate().isAfter(budget.getEndDate())) {
            IOHandler.writeWarning(
                    "Notice: The expense you wish to deduct from budget is " +
                            "not in the time frame of the budget.");
        }
    }

    public void removeExpenseFromBudget(Expense expense) {
        // Find the budget that matches the expense category.
        Budget targetBudget;
        try {
            targetBudget = getBudgetForCategory(expense.getCategory())
                    .orElseThrow(() -> new BudgetRuntimeException("Expense is not in any of the budget category"));
            targetBudget.removeExpenseFromBudget(expense);
            budgetStorage.saveFile(new ArrayList<>(budgets));
        } catch (BudgetRuntimeException e) {
            IOHandler.writeError(e.getMessage());
        }
    }

    public boolean modifyExpenseInBudget(Expense oldExpense, Expense newExpense){
        removeExpenseFromBudget(oldExpense);
        return deductBudgetFromExpense(newExpense);
    }

    public Budget getBudget(int index) {
        if (index < 0 || index >= budgets.size()) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        return budgets.get(index);
    }

    public void modifyBudget(int index, String name, double amount, LocalDate endDate, String category)
            throws BudgetRuntimeException {
        if (index < 0 || index >= budgets.size()) {
            throw new BudgetRuntimeException("You have entered an invalid index. Please try again");
        }
        Budget b = budgets.get(index);
        String oldCategory = b.getCategory();
        if (category != null && !oldCategory.equals(category)) {
            if (budgetByCategory.containsKey(category)) {
                throw new BudgetRuntimeException("Budget in category " + category + " already exists.");
            }
            budgetByCategory.remove(oldCategory);
            budgetByCategory.put(category, b);
        }
        b.modifyBudget(amount, name, endDate, category);
        budgetStorage.saveFile(new ArrayList<>(budgets));
    }

    //to list out all the expenses within the budget
    //incorporate it in command
    public void checkBudget(int index) {
        if (index < 0 || index >= budgets.size()) {
            IOHandler.writeOutput("Index out of range.");
            return;
        }
        IOHandler.writeOutput(budgets.get(index).printExpenses());
    }

    @Override
    public ArrayList<Finance> getBudgetList() {
        return new ArrayList<>(budgets);
    }

}
