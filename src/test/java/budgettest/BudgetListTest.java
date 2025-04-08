package budgettest;

import budgetsaving.budget.Budget;
import budgetsaving.budget.BudgetList;
import budgetsaving.budget.exceptions.BudgetException;
import budgetsaving.budget.exceptions.BudgetRuntimeException;
import cashflow.model.storage.Storage;
import cashflow.model.interfaces.Finance;
import expenseincome.expense.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.io.IOHandler;
import utils.money.Money;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.io.FileNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BudgetListTest {

    private BudgetList budgetList;
    private Currency currency;
    private Storage dummyStorage;

    @BeforeEach
    void setUp() {
        // Use USD as the currency.
        currency = Currency.getInstance("USD");
        // Create a dummy Storage instance using an anonymous subclass.
        // This dummy overrides loadFile() and saveFile() to avoid real file I/O.
        dummyStorage = new Storage("dummyFilePath") {
            // For verification, we capture the list that would be "saved" in this field.
            private ArrayList<Finance> savedFile = new ArrayList<>();

            @Override
            public ArrayList<Finance> loadFile() {
                System.out.println("Dummy loadFile called.");
                // Simply return an empty list during tests.
                return new ArrayList<>();
            }

            @Override
            public void saveFile(ArrayList<Finance> financeList) {
                // Capture the finance list; do nothing else.
                savedFile = new ArrayList<>(financeList);
                System.out.println("Dummy saveFile called with " + financeList.size() + " item(s).");
            }

            // Optional getter for savedFile if you want to verify file contents.
            public ArrayList<Finance> getSavedFile() {
                return savedFile;
            }
        };

        try {
            // Instantiate BudgetList using the constructor that accepts a Storage.
            budgetList = new BudgetList(currency, dummyStorage);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during setup: " + e.getMessage());
        }
    }

    //====================
    // Existing Test Cases
    //====================

    @Test
    void testSetBudget() {
        int initialSize = budgetList.getBudgetList().size();
        // setBudget creates a new Budget and internally calls dummyStorage.saveFile(...)
        budgetList.setBudget("Food", 500.0, LocalDate.now().plusDays(10), "Essentials");
        int newSize = budgetList.getBudgetList().size();
        assertEquals(initialSize + 1, newSize, "Budget list should have one more element after adding a new budget.");
    }

    @Test
    void testDeductFromBudget() {
        // Add a budget with 1000.0 USD in total.
        budgetList.setBudget("Utilities", 1000.0, LocalDate.now().plusDays(15), "Bills");
        Budget budget = budgetList.getBudget(0);
        double initialRemaining = budget.getRemainingBudget().getAmount().doubleValue();
        // Deduct 200 USD from the budget.
        budgetList.deductFromBudget(0, 200.0);
        double newRemaining = budget.getRemainingBudget().getAmount().doubleValue();
        // The remaining amount should decrease by 200.
        assertEquals(initialRemaining - 200.0, newRemaining, 0.01, "Remaining budget should be reduced by the deducted amount.");
    }

    @Test
    void testListBudgets() {
        // Initially, the budget list is expected to be empty.
        assertTrue(budgetList.getBudgetList().isEmpty(), "Budget list should be initially empty.");
        // Add a budget and then retrieve the list.
        budgetList.setBudget("Entertainment", 300.0, LocalDate.now().plusDays(5), "Fun");
        ArrayList<Finance> list = budgetList.getBudgetList();
        assertNotNull(list, "Budget list should not be null.");
        assertEquals(1, list.size(), "Budget list should contain one item after adding a budget.");
    }

    @Test
    void testGetBudgetWithValidIndex() {
        // Add a new budget.
        budgetList.setBudget("Travel", 800.0, LocalDate.now().plusDays(12), "Leisure");
        Budget budget = budgetList.getBudget(0);
        assertNotNull(budget, "Budget at index 0 should not be null.");
        assertEquals("Travel", budget.getName(), "Budget name should match the one provided.");
    }

    @Test
    void testGetBudgetWithInvalidIndex() {
        // Without adding any budget, trying to get one should throw IndexOutOfBoundsException.
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            budgetList.getBudget(0);
        });
        assertNotNull(exception, "An IndexOutOfBoundsException should be thrown for an invalid index.");
    }

    @Test
    void testModifyBudget() {
        // Add a budget, then modify it.
        budgetList.setBudget("Shopping", 600.0, LocalDate.now().plusDays(20), "Daily");
        Budget originalBudget = budgetList.getBudget(0);
        // Define new attributes.
        String newName = "New Shopping";
        double newAmount = 700.0;
        LocalDate newEndDate = LocalDate.now().plusDays(30);
        String newCategory = "Groceries";
        // Modify the budget at index 0.
        try {
            budgetList.modifyBudget(0, newName, newAmount, newEndDate, newCategory);
        } catch (Exception e) {
            IOHandler.writeError(e.getMessage());
        }
        Budget modifiedBudget = budgetList.getBudget(0);
        assertEquals(newName, modifiedBudget.getName(), "Budget name should be updated.");
        assertEquals(newAmount, modifiedBudget.getAmount(), 0.01, "Budget amount should be updated.");
        assertEquals(newEndDate, modifiedBudget.getEndDate(), "Budget end date should be updated.");
        assertEquals(newCategory, modifiedBudget.getCategory(), "Budget category should be updated.");
    }

    @Test
    void testAddNewBudget_Duplicate() {
        // Capture the System.err output.
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        budgetList.setBudget("DuplicateBudget", 400.0, LocalDate.now().plusDays(10), "Dup");

        budgetList.setBudget("Another Budget", 500.0, LocalDate.now().plusDays(12), "dup");

        System.setErr(originalErr);

        String errorOutput = errContent.toString();
        System.out.println("Captured error output: " + errorOutput);

        assertTrue(errorOutput.contains("already exists"),
                "Output should contain 'already exists' error message.");
    }



    @Test
    void testDeductBudgetFromExpense_Exceed() {
        // Add a budget with a small total amount.
        budgetList.setBudget("ExpenseTest", 100.0, LocalDate.now().plusDays(5),
                "TestCat");
        Budget budget = budgetList.getBudget(0);
        double initialRemaining = budget.getRemainingBudget().getAmount().doubleValue();
        // Create an expense that exceeds the budget.
        Expense expense = new Expense("Expensive Meal", new Money(currency, BigDecimal.valueOf(150.0)),
                LocalDate.now(), "TestCat");
        boolean exceeded = budgetList.deductBudgetFromExpense(expense);
        // Expect the remaining amount to become negative and the method to return true.
        assertTrue(exceeded, "deductBudgetFromExpense should return true when budget is exceeded.");
    }

    @Test
    void testDeductBudgetFromExpense_NotExceed() {
        // Add a budget with a relatively high total amount.
        budgetList.setBudget("ExpenseTest", 500.0, LocalDate.now().plusDays(5),
                "TestCat");
        Budget budget = budgetList.getBudget(0);
        double initialRemaining = budget.getRemainingBudget().getAmount().doubleValue();
        // Create an expense that deducts a small amount.
        Expense expense = new Expense("Meal", new Money(currency, BigDecimal.valueOf(100.0)),
                LocalDate.now(), "TestCat");
        boolean exceeded = budgetList.deductBudgetFromExpense(expense);
        // The method should return false when the budget is not exceeded.
        assertFalse(exceeded, "deductBudgetFromExpense should return false when budget is not exceeded.");
    }

    @Test
    void testRemoveExpenseInBudget_NotFound() {
        // Create an expense for a category that doesn't exist in any budget.
        Expense expense = new Expense("Coffee", new Money(currency, BigDecimal.valueOf(50.0)),
                LocalDate.now(), "NonExisting");
        boolean result = budgetList.removeExpenseInBudget(expense);
        assertFalse(result, "removeExpenseInBudget should return false when no budget" +
                " matches the expense category.");
    }

    @Test
    void testModifyExpenseInBudget() {
        // Add a budget for a specific category.
        budgetList.setBudget("ModifyExpenseTest", 300.0, LocalDate.now().plusDays(5),
                "ModCat");
        // Create two Expense objects with the same category.
        Expense oldExpense = new Expense("Dinner", new Money(currency, BigDecimal.valueOf(50.0)),
                LocalDate.now(), "ModCat");
        Expense newExpense = new Expense("Dinner Updated", new Money(currency, BigDecimal.valueOf(100.0)),
                LocalDate.now(), "ModCat");
        // Call modifyExpenseInBudget and capture the returned boolean.
        boolean result = budgetList.modifyExpenseInBudget(oldExpense, newExpense);
        // When the updated expense does not exceed the budget, we expect false.
        assertFalse(result, "modifyExpenseInBudget should return false when budget is" +
                " not exceeded after modification.");
    }

    @Test
    void testCheckBudget_InvalidIndex() {
        // Call checkBudget with an invalid index.
        // This method writes output via IOHandler but should not throw an exception.
        try {
            budgetList.checkBudget(0);  // When budget list is empty, index 0 is invalid.
        } catch (Exception e) {
            fail("checkBudget should not throw an exception even if the index is invalid.");
        }
    }

    @Test
    void testSetAndGetCurrency() {
        // Verify that the getter and setter for currency work correctly.
        assertEquals(currency, budgetList.getCurrency(), "Initial currency should be USD.");
        Currency newCurrency = Currency.getInstance("EUR");
        budgetList.setCurrency(newCurrency);
        assertEquals(newCurrency, budgetList.getCurrency(), "Currency should be updated to EUR.");
    }
}
