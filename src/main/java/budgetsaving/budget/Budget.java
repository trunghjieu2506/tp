package budgetsaving.budget;

import budgetsaving.budget.exceptions.BudgetRuntimeException;
import budgetsaving.budget.utils.BudgetActiveStatus;
import budgetsaving.budget.utils.BudgetExceedStatus;
import budgetsaving.budget.utils.BudgetSerialiser;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;
import utils.money.Money;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;


public class Budget extends Finance {
    private String name;
    private Money totalBudget;
    private Money remainingBudget;
    private ArrayList<Expense> expenses;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private BudgetActiveStatus activeStatus;
    private BudgetExceedStatus exceedStatus;

    public Budget(String name, Money totalBudget, LocalDate endDate, String category) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Budget name cannot be null or empty.");
        }
        if (totalBudget == null) {
            throw new IllegalArgumentException("Total budget cannot be null.");
        }
        if (totalBudget.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total budget amount cannot be negative.");
        }
        if (totalBudget.getCurrency() == null) {
            throw new IllegalArgumentException("Total budget currency cannot be null or empty.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (endDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End date must be in the future.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        this.name = name;
        this.totalBudget = totalBudget;
        this.expenses = new ArrayList<>();
        this.startDate = LocalDate.now();
        this.endDate = endDate;
        this.category = category;
        this.remainingBudget = new Money(totalBudget.getCurrency(), totalBudget.getAmount());
        this.activeStatus = BudgetActiveStatus.ACTIVE;
        this.exceedStatus = BudgetExceedStatus.HAS_REMAINING_BUDGET;
    }

    // Getter for budget name
    public String getName() {
        return name;
    }

    public BigDecimal getMoneySpent() {
        return totalBudget.getAmount().subtract(remainingBudget.getAmount());
    }

    public Money getRemainingBudget() {
        return this.remainingBudget;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public BudgetActiveStatus getBudgetActiveStatus(){
        return this.activeStatus;
    }


    public void updateBudgetActiveStatus(BudgetActiveStatus status) {
        this.activeStatus = status;
    }

    public void updateBudgetExceedStatus(BudgetExceedStatus exceedStatus) {
        this.exceedStatus = exceedStatus;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    // Setters now accept a double and convert it to a BigDecimal internally
    public void setTotalBudget(double amount) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        totalBudget.setAmount(amt);
    }


    public void setRemainingBudget(double amount) {
        BigDecimal amt = BigDecimal.valueOf(amount);
        remainingBudget.setAmount(amt);
    }


    // Deducts an amount from the remaining budget
    public void deduct(double amount) {
        BigDecimal deduction = BigDecimal.valueOf(amount);
        BigDecimal current = remainingBudget.getAmount();
        remainingBudget.setAmount(current.subtract(deduction));
        if (remainingBudget.getAmount().doubleValue() < 0) {
            this.exceedStatus = BudgetExceedStatus.EXCEEDED_BUDGET;
        }
    }

    // Adds an amount to both the total and remaining budget
    public void add(double amount) {
        BigDecimal addition = BigDecimal.valueOf(amount);
        remainingBudget.setAmount(remainingBudget.getAmount().add(addition));
        totalBudget.setAmount(totalBudget.getAmount().add(addition));
    }


    //to be used by expense, when adding a new expense
    //if true, then still have budget, else exceeded budget
    public boolean deductFromExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Invalid expense.");
        }
        if (expense.getAmount() < 0) {
            throw new IllegalArgumentException("Expense amount cannot be negative.");
        }
        if (expenses.add(expense)) {
            deduct(expense.getAmount());
            BigDecimal remainingAmount = remainingBudget.getAmount();
            double remainingAmountDouble = remainingAmount.doubleValue();
            exceedStatus = remainingAmountDouble > 0 ?
                    BudgetExceedStatus.HAS_REMAINING_BUDGET : exceedStatus;
            return remainingAmountDouble < 0;
        }
        throw new IllegalStateException("Error adding the expense to the budget.");
    }


    public void removeExpenseFromBudget(Expense expense) throws BudgetRuntimeException {
        if (expense == null) {
            throw new BudgetRuntimeException("Invalid expense.");
        }
        if (!expenses.contains(expense)) {
            throw new BudgetRuntimeException("Expense not found in the budget.");
        }
        if (expenses.remove(expense)) {
            BigDecimal amount = BigDecimal.valueOf(expense.getAmount());
            remainingBudget.setAmount(remainingBudget.getAmount().add(amount));
        }
    }


    //If do not modify one of the attributes, call the method with
    //totalAmount = 0, and name = null
    public void modifyBudget(double totalAmount, String name, LocalDate endDate, String category) {
        if (totalAmount > 0) {
            BigDecimal spent = getMoneySpent();
            if (BigDecimal.valueOf(totalAmount).compareTo(spent) < 0) {
                throw new IllegalArgumentException("Total amount cannot be less than money already spent.");
            }
            setTotalBudget(totalAmount);
            double updatedRemaining = totalAmount - spent.doubleValue();
            setRemainingBudget(updatedRemaining);
            if (updatedRemaining > 0) {
                updateBudgetExceedStatus(BudgetExceedStatus.HAS_REMAINING_BUDGET);
            }
        }
        if (name != null) {
            this.name = name;
        }
        if (endDate != null) {
            this.endDate = endDate;
        }
        if (category != null) {
            this.category = category;
        }
    }


    @Override
    public String toString() {
        return  "[" + activeStatus + "]" + "[" + exceedStatus + "]" + "{Name: " + name +
                ", Category: " + category +
                ", RemainingBudget: " + remainingBudget.toString() +
                ", From " + startDate + " to " + endDate + "}";
    }

    public String printExpenses() {
        StringBuilder sb = new StringBuilder();
        sb.append(this);
        if (expenses.isEmpty()) {
            sb.append("\n\tThere are no expenses in this budget yet");
        } else {
            for (Expense expense : expenses) {
                sb.append( "\t " + expense.toString());
            }
        }
        return sb.toString();
    }

    public String serialiseToString() {
        return BudgetSerialiser.serialise(this);
    }

    @Override
    public LocalDate getDate() {
        return this.endDate;
    }

    @Override
    public double getAmount(){
        return this.totalBudget.getAmount().doubleValue();
    }

    @Override
    public String getType() {
        return this.category;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
}
