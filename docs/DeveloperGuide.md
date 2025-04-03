# Developer Guide

## Acknowledgements

- The command parsing design is inspired by the Command Pattern described in the SE-EDU AddressBook-Level3.
- Parts of the error-handling and assertion patterns were referenced from Oracle’s Java tutorials.
- Logger setup referenced from standard Java logging documentation.

## Design & implementation

### 1. Nicholas

Responsible for implementing the **Expense** and **Income** management modules. This includes the `Expense`, `Income`, `ExpenseManager`, `IncomeManager`, as well as all associated commands and parsers.

The goal of these modules is to allow users to accurately and efficiently manage their expenses and income with support for adding, editing, deleting, listing, categorizing, and sorting entries.

#### Expense and Income Structure

Each of the two core entities — Expense and Income — is represented by its own class (`Expense`, `Income`) and managed through its own manager class (`ExpenseManager`, `IncomeManager`).
The below shows the high-level class diagram:

![Expense and Income Structure](img_2.png)

#### Command Parsing and Execution

Each command extends from an abstract `ExpenseCommand` or `IncomeCommand` base class and overrides the `execute(...)` method.

Example sequence diagram for the command `add Lunch 12.5 Food 2025-03-25`:

![Add Expense Command Sequence](img_1.png)

This design simplifies testing, separates concerns clearly, and is extensible to other commands (e.g., filtering by date range in the future).

#### Key Implementation Choices

- **Command Pattern:** Each command is encapsulated in its own class with a single `execute(...)` method. This makes it easier to test and extend in the future.
- **Category Normalization:** Category strings are capitalized by the parser to ensure consistent matching.
- **Robust Input Parsing:** The parser uses defensive programming to avoid crashing on invalid user input, providing feedback when inputs are incomplete or incorrect.

#### Alternatives Considered

- Instead of a separate command class for each operation, we considered a generic `Command` with a type string and arguments. However, this would reduce readability and make each command's logic more error-prone.
- We considered storing all entries in a shared superclass like `FinancialEntry`, but this was not implemented as Expenses and Incomes may diverge in fields and behavior later.

#### Future Enhancements

- Support for recurring entries.
- Advanced search and filtering (e.g., date ranges).
- UI display of graphs or trends.


### 2. Hongheng
   Responsible for implementing the Budget and Saving management modules. This includes the Budget, Saving, BudgetList, SavingList, and the associated commands and parsers.

The goal of these modules is to allow users to manage budgets and track savings efficiently, with support for adding, editing, deleting, listing, and viewing summary data.

#### Budget and Saving Structure
Each of the two entities — Budget and Saving — is represented by its own class and managed through its own container class (BudgetList, SavingList).

The diagram below shows the high-level class structure:

![BudgetManager.png](team/BudgetManager.png)


Budget: Represents a budget allocation for a specific month, storing:

month: YearMonth

amount: double

description: String (optional)

Saving: Represents a saved fund entry, storing:

date: LocalDate

amount: double

purpose: String

BudgetList and SavingList: Handle collections of budgets and savings, providing utilities like filtering and summarizing.

#### Command Parsing and Execution
Each command related to Budget or Saving extends from a common Command base class and overrides the execute(Model model) method.

#### Example sequence diagram for the command:
add-budget m/2025-04 a/1000 d/April allowance


Note: Replace with actual PNG exported from your PlantUML diagram.

#### Flow:

The user inputs the add-budget command.

LogicManager passes it to AddBudgetCommandParser.

A Budget object is created.

AddBudgetCommand is constructed with the budget.

execute(model) adds the budget to the model.

The UI reflects the update.

#### Savings commands like add-saving follow a similar pattern.


![Saving.png](team/Saving.png)

### Testing


#### Key Implementation Choices
Domain Classes: Having separate Budget and Saving classes allows clearer separation of concerns and future flexibility.

JavaFX Bindings: Changes to ObservableList are reflected live in the UI.

Date Parsing: Uses YearMonth and LocalDate to ensure correct input validation and display formatting.

#### Alternatives Considered
Using a shared superclass like FinancialEntry to generalize Budget and Saving.

- Rejected to avoid premature generalization and preserve domain-specific logic.

Managing budget/saving lists directly inside ModelManager.

- Rejected in favor of separate BudgetList and SavingList classes to improve modularity and unit testing.

#### Future Enhancements
Support for recurring budgets (e.g., auto-renewing monthly budgets)

Budget vs actual visualization using bar/line graphs

Add filters for purpose, amount, and date/month

Export functionality (e.g., CSV or Excel)

### 3. Qiaozi

Responsible for implementing the **Loan**, **Contact** and **Money** management modules. This includes the `Loan`, `LoanManager`, `Person`, `ContactList`, `Money`, as well as all associated commands and parsers.

The goal of these modules is to allow users to accurately and efficiently manage their loans with support for adding, editing, deleting, listing, and finding entries.

#### Loan Structure

The loans can be classified as either as a Simple Bullet Loan or an Advanced Loan. Each type of loan inherits from an abstract class Loan. All loans are managed by the LoanManager class. 



## Product scope
### Target user profile

- CLI users who prefer keyboard-based interactions.
- Budget-conscious individuals tracking daily spending.
- Students or working professionals managing personal finances.

### Value proposition

- Easy and fast recording of transactions.
- No setup or signup — works locally and offline.
- Lightweight and highly customizable.

## User Stories

| Version | As a ... | I want to ... | So that I can ...|
|---------|----------|---------------|------------------|
| v1.0    |new user|see usage instructions|refer to them when I forget how to use the application|
| v1.0    |user|find a to-do item by name|locate a to-do without having to go through the entire list|
| v1.0    |university student with limited finances|set a monthly budget|avoid overspending|
| v1.0    |university student who wants to be secure financially|set savings goals (e.g., $500 for a trip)|motivate myself to save consistently|
| v1.0    |student with extra budget left|place part of my money into the saving goal|reach my goal and see my progress|
| v1.0    |university student who often wonders about how much I saved|check regularly my saving goals|get a clearer understanding of my progress|
| v2.0    |student who completed saving goals|get alert and achievement from the system for completion|be motivated by my progress|
| v2.0    |student who faces inconsistent income|modify my saving goals|adapt to my current financial situation|
| v2.0    |student|restrict myself from overspending in a specific category and set a budget on that category|better monitor my expenses and plan my budget more efficiently|
| v2.0    |student|receive alerts when I am close to exceeding my budget|adjust my spending in time|
| v2.0    |user|see inline suggestions when typing commands|navigate commands more easily|
| v2.1    |student who completed saving goals|get a saving summary when I complete my goal|know how long it took and my average saving per day|
| v2.1    |student who is busy with my work|set recurring budget and saving goal|avoid manually adding them, which I might forget|




## Non-Functional Requirements

- Runs on any OS with Java 17+.
- Handles up to 1000 entries without noticeable lag.
- Should respond to commands within 1 second.
- CLI-based, with no need for internet connection.

## Glossary

| Term | Definition |
|------|------------|
| CLI | Command-Line Interface |
| Command Pattern | A design pattern in which each action is encapsulated in its own class |
| Category | A label like Food, Transport, Job, etc., used to group entries |


## Instructions for manual testing

The following steps help a tester verify the correctness of features:

1. **Add an expense:**
   ```
   add Coffee 4.5 Drink 2025-04-01
   ```
    - Expected: "Added: Coffee - $4.5 on 2025-04-01 [Category: Drink]"
   

2. **List expenses:**
   ```
   list
   ```
    - Expected: Display list with the added expense.
   

3. **Edit an expense:**
   ```
   edit 1 Tea 3.0 Drink 2025-04-02
   ```
    - Expected: Entry should be updated to "Tea - $3.0 ..."
   

4. **Delete an expense:**
   ```
   delete 1
   ```
    - Expected: Entry is removed.
   

5. **Add income:**
   ```
   add Salary 2000 Job
   ```

6. **List incomes:**
   ```
   list
   ```

7. **Sort income by date:**
   ```
   sort recent
   ```

8. **Test top/bottom category analysis:**
   ```
   top
   bottom
   ```

Note: Manual testing does not persist data unless storage is implemented. Re-adding entries is required after restarting the app.