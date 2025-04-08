# Developer Guide

## Table of Contents

- [Design](#design)
   - [Expense Management](#expense-management)
   - [Income Management](#income-management)
   - [Budget Management](#budget-management)
   - [Saving Management](#saving-management)
   - [Loan Management](#loan-management)
   - [Application Management](#application-management)
- [Implementation](#implementation)
   - [Expense](#expense-)
   - [Income](#income)
   - [Saving](#saving-)
   - [Budget](#budget-)
   - [Loan](#loan)
  -  [Analytic](#analytic-command)
- [Appendix A: Product Scope](#appendix-a-product-scope)
- [Appendix B: User Stories](#appendix-b-user-stories)
- [Appendix C: Non-Functional Requirements](#appendix-c-non-functional-requirements)
- [Appendix D: Glossary](#appendix-d-glossary)
- [Appendix E: Instructions for Manual Testing](#appendix-e-instructions-for-manual-testing)
- [Acknowledgements](#acknowledgements)

---

## Design

### Expense Management

The **Expense** modules is designed to provide full support for managing user expenses within the CashFlow application. It includes classes for command parsing, command execution, business logic, and error handling.

This module follows the **Command Pattern** for extensibility, the **Separation of Concerns** principle for maintainability, and includes robust user feedback mechanisms.

### Key Classes and Relationships

The diagram shows the how different classes within the expense package interacts with one another:

![ExpensePackageClassDiagram](img_6.png)

---

### Income Management

The **Income** module is responsible for managing user incomes within the CashFlow application. It mirrors the structure and principles of the Expense module, supporting features like adding, editing, deleting, listing, sorting, and category analysis for income entries.

This module follows the Command Pattern to organize operations, promotes Separation of Concerns, and ensures user-friendly parsing and error reporting.

---

### Key Classes and Relationships

The diagram below shows how different classes in the income package interact:

![IncomePackageClassDiagram](img_5.png)
---

### Design Principles for both Expense and Income

- **Modularity**: Each command is encapsulated in its own class.
- **Single Responsibility**: Parsing, validation, and execution are delegated to separate classes.
- **Feedback-Oriented**: Parsers return structured results to inform users of errors without crashing the program.
- **Logging**: Uses `java.util.logging` to trace important events, hidden from the user interface.

---

### Budget Management

#### Architecture 
The Budget Management system is designed to handle all budget commands, and it also integrated with the
expense management system, to automatically track expenses and manage budget.

Below is the overview of the Budget system. It illustrates the architecture and the procedure of 
a budget command being executed.

BudgetList implements BudgetManager, and holds an Arraylist of Budgets.
The user input a string, and it parsed by BudgetAttribute, BudgetParser classes to return a command.
The command is then executed by calling methods from BudgetList.
Budget, Budgetlist throws BudgetExceptions which are catch inside Budget Commands classes.

![BudgetManager.png](team/BudgetManager.png)

#### Flow of the commands
To execute a command, the user first type in the CLI. The input is captured by the IOHandler class,
which then passes in the input to the BudgetGeneralCommand, which will handle all the different commands
in budget. The class then decides which command to execute based on the first keyword by the user.

The a new, specific command is created, by passing the input into the BudgetParser class, which then pass
the input to BudgetAttribute class. The reason for this is that: we can use a single class to handle all
input attributes, such as `n/`, `a/`, and return it to the parser for further processing.

The BudgetParser takes in the attributes, then select the required attribute. In this process, exceptions
are thrown as BudgetParserException, which happens in the class BudgetParser, or BudgetAttributeException,
which are exceptions found by the BudgetAttribute class.

The command then proceed to execute, which calls the method in BudgetList, and subsequently Budget for the commands.

The following sequence diagram illustrates a general flow of the Budget Management system's command.
![BudgetManagerSequence](BudgetSavingSequence.png)

#### Exception Handling

To properly handle exceptions and output the details of the error, so that users can quickly identify the error in input,
the exceptions are separated into many different child classes of the Exception class. A multi-level inheritance is used
where BudgetException inherits from Exceptions, while each details exceptions inherit from BudgetException.

Each detailed exception will have its own unique outputs. Which help us to identify, at which stage in the sequence diagram
did an exception happened.

![BudgetExceptions](BugdetExceptions.png)

---

### Saving Management
Responsible for implementing the Budget and Saving management modules. 
This includes the Saving, SavingList, and the associated commands and parsers.

The goal of these modules is to allow users to manage budgets and track savings efficiently, 
with support for adding, editing, deleting, listing, and viewing summary data.

#### Saving Architecture and Sequential Flow
The Saving Management system flow and design architecture is very similar to the Budget Management System, except that the
methods called in the Saving and SavingList are different. 
Therefore please refer to the diagrams in Budget above. Details will be explained inside the implementation.


---

### Loan Management
This includes the `Loan`, `Interest`, `LoanManager` and associated commands and parsers.

The goal of these modules is to allow users accurately and efficiently manage their loans, as well as loans between other people. The module supports the adding, editing, deleting, listing, tagging, sorting and searching entries.

#### Loan Structure

Every type of loan inherits from the abstract `Loan` class, which contains universal attributes and methods for all loans. The loans are managed by the manager class `LoanManager`, which stores loans in an `ArrayList`.

#### Interest Structure

Advanced Bullet Loans that apply interests would each refer to an `Interest` class, which specifies how the interest is applied.

The diagram below shows the high-level structure of core classes:

![LoanManager.png](images/LoanManager.png)

#### Command Parsing and Execution

To carry out operations on the loans, the user inputs are read through the `LoanUI` class and parsed by the `LoanCommandParser` class, which generates commands based on the user's inputs.

Each command extends from an abstract `LoanCommand` base class and overrides the `execute()` method.

Due to the large number of attributes in each `Loan` class, the parser would ask for inputs sequentially.

The diagram below shows the high-level structure of loan commands:

![LoanCommands.png](images/LoanCommands.png)

--- 

### Other utilities

#### Money
A `Money` class is created to standardise the management of each unit of money. The Currency class for 

![Money.png](images/Money.png)
#### Tags
![TagList.png](images/TagList.png)
---
### Application Management
CashFlowManager is the central coordinator for initializing and running the core features of the CashFlow application. It sets up data persistence, managers for different financial modules (e.g., expense, income, savings), and launches the CLI-based UI loop.

#### Class Responsibilities
1. Load persistent storage and configuration files.

2. Initialize the following managers:

- ExpenseManager, IncomeManager, SavingList, BudgetList, LoanManager

- AnalyticsManager to manage Analytic Program
- SetUpManager to manage settings configuration
3. Set up FinanceData with initialized managers for dependency injection.

4. Handle first-time user setup with SetUpCommand.

5. Start and control the main UI loop (UI.run()).

#### Class Diagram

Here is a class diagram of CashFlowManager (references among CashFlowManager and other managers such as ExpenseManager are redacted for ease of view)

![CashFlowManager.png](CashFlowManager.png)

### Analytics Management
The AnalyticsManager is the core component responsible for computing financial insights for the user in CashFlow. It analyzes income and expense data from the system and provides summaries, trend analysis, and breakdowns in a human-readable format.

AnalyticsManager is the 

#### Class Responsibilities
AnalyticManager - main entry point for all analytic operations:

1. Generate monthly summaries (getMonthlySummary)

2. Print trends over time (showTrendOverTime)

3. Provide spending insights (showSpendingInsights)

4. Display expense breakdowns by category (showCategoryBreakdown)

AnalyticDataLoader - A utility class used internally by AnalyticsManager to:

1. Retrieve all transactions for a specific month or date range

2. Compute total income, expenses, and net savings

3. Filter and aggregate expense data
#### Class Diagram

Here is a class diagram of AnalyticsManager

![AnalyticManager.png](AnalyticManager.png)

--- 
## Implementation

### Expense 

Represents a single expense entry with attributes:
- `desc`: What the expense is.
- `amount`: A `Money` object representing the amount.
- `date`: When the expense was added.
- `category`: A tag to group the expense.

### ExpenseCommandParser

- Responsible for parsing and validating commands entered in **expense mode**.
- Produces `ExpenseParserResult` containing either a valid command or a user-friendly error message.
- Supports commands:
   - `add <description> <amount> <category> [yyyy-mm-dd]`
   - `edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]`
   - `delete <index>`
   - `list` / `list category <category>`
   - `sort recent` / `sort oldest`
   - `top` / `bottom`
   - `help`

### ExpenseParserResult

- Contains two fields:
   - `command`: an `ExpenseCommand` object (if parsed successfully).
   - `feedback`: a `String` containing an error message (if any issue occurs).

### ExpenseManager

- Stores the list of expenses and manages business logic.
- Methods include:
   - `addExpense(...)`: Validates and adds new expense.
   - `editExpense(...)`: Updates an existing expense by index.
   - `deleteExpense(...)`: Removes expense by index.
   - `listExpenses()` and `listExpensesByCategory(...)`: Lists all or filtered expenses.
   - `sortExpensesByDate(...)`: Sorts by date.
   - `printTopCategory()` / `printBottomCategory()`: Computes category statistics.

### ExpenseCommand and Subclasses

Each command like `AddExpenseCommand`, `EditExpenseCommand`, `DeleteExpenseCommand` extends `ExpenseCommand` and overrides `execute(ExpenseManager manager)`.

For example, `AddExpenseCommand`:
```java
public void execute(ExpenseManager manager) {
    manager.addExpense(description, amount, date, category);
}
```

This makes testing and future enhancements (e.g. undo/redo) straightforward.

An example of the sequence diagram for Add Expense Command is as shown:
![Add Expense Command Sequence](img_7.png)

---

### Income

Represents a single income entry with attributes:
- `source`: Where the income came from.
- `amount`: A `Money` object representing the amount.
- `date`: When the income was received.
- `category`: A tag to group the income.

### IncomeCommandParser

- Responsible for parsing commands entered in **income mode**.
- Returns an `IncomeParserResult` object containing a valid command or an error message.
- Supports commands:
   - `add <source> <amount> <category> [yyyy-mm-dd]`
   - `edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]`
   - `delete <index>`
   - `list` / `list category <category>`
   - `sort recent` / `sort oldest`
   - `top` / `bottom`
   - `help`

### IncomeParserResult

- Acts as a structured container for parsing outcomes:
   - `command`: The valid `IncomeCommand` if parsing succeeds.
   - `feedback`: Error message string to show user if parsing fails.

### IncomeManager

- Maintains an `ArrayList<Income>` and implements business logic:
   - `addIncome(...)`: Adds new income after validation.
   - `editIncome(...)`: Updates an income entry by index.
   - `deleteIncome(...)`: Removes income at specified index.
   - `listIncomes()` / `listIncomesByCategory(...)`: Lists all or filtered incomes.
   - `sortIncomesByDate(...)`: Sorts by recent or oldest.
   - `printTopCategory()` / `printBottomCategory()`: Computes income statistics.

### IncomeCommand and Subclasses

Each specific income action is encapsulated in its own class, extending `IncomeCommand`. For example, `AddIncomeCommand`, `EditIncomeCommand`, `DeleteIncomeCommand` implement their own `execute(IncomeManager manager)` method.

Example from `EditIncomeCommand`:
```java
public void execute(IncomeManager manager) {
   manager.editIncome(index, newSource, newAmount, newDate, newCategory);
}
```

This makes the logic modular, testable, and easily extendable.

An example of the sequence diagram for Edit Income Command is as shown:
![Edit Income Command Sequence](img_8.png)
---
### Error Handling for Expense and Income

- Custom exceptions via `ExpenseException` and `IncomeException` to handle user input validation.
- Example validations:
   - Description must not be empty.
   - Amount must be positive.
   - Index must be within list bounds.
- Parser handles syntax and structure validation; Manager handles business rule validation.

### Logging for Expense and Income

- Internally uses Java’s `Logger` to log all state-changing operations.
- Warnings are logged but not shown to users unless necessary.

---

### Budget 
#### Overview of the Budget Class
The Budget contains:
```
- name: String
- totalAmount: Money
- RemainingAmount: Money
- expenses: ArrayList<Expense>
- deadline: LocalDate
- category: String
- and BudgetCompletionStatus, BudgetExceedStatus
```
The exact manipulation of the Budget Management system would be too long to describe here. In this section I will only 
show the details of a few interesting class that I have implemented, and also some proposed features that is not 
implemented yet, but will do it in the future.

#### Budget Attributes
The class currently supports the following input identifiers:

| Identifier | Field        | Type         | Description                             |
|------------|--------------|--------------|-----------------------------------------|
| `i/`       | `index`      | `int`        | 0-based index for identifying a budget  |
| `n/`       | `name`       | `String`     | Budget name                             |
| `a/`       | `amount`     | `double`     | Budget amount                           |
| `e/`       | `endDate`    | `LocalDate`  | End date of the budget (format: YYYY-MM-DD) |
| `c/`       | `category`   | `String`     | Category associated with the budget     |

1. **Input String Received**  
   The constructor of `BudgetAttributes` takes in a raw input string (e.g., `"n/Groceries a/500.0 e/2025-12-31 c/Food"`).

2. **Identifier Order & Duplication Check**
  - It ensures no identifier is repeated.
  - It checks that identifiers appear in strictly increasing order.

3. **Field Extraction & Validation**  
   The identifiers must follow these rules:
   - Amount must be greater than or equal to 0.01, and it should be a real number
   - Any decimals after 0.01 in amount is ignored. For example, `0.00103` will be parsed as just `0.01`
   - Date must strictly follow the format YYYY-MM-DD
   - Date input must be after the current date
   - Index must be a positive integer, ranging from 1 to the size of the list, depending on the list type
   - Strings must not be empty, if the identifier is declared
   
   **Note** that during BudgetAttribute parsing, 1-index base is converted to 0-index base

4. **Parsed Object**  
   The class returns itself after parsing, containing all valid fields ready for use.



The design choice to have `BudgetAttributes` return itself after construction makes it easily reusable:

- **Command classes** do not need to deal with parsing logic.
- Centralized validation ensures consistency across different parts of the application.
- Easier unit testing of parsing logic.
- The same class supports all command variants (e.g., those requiring only `i/` or only `c/`).

#### Example Usage

```java
String input = "n/Trip a/1000 e/2025-12-31 c/Travel";
BudgetAttributes attributes = new BudgetAttributes(input);

String name = attributes.getName();          // "Trip"
double amount = attributes.getAmount();      // 1000.0
LocalDate end = attributes.getEndDate();     // 2025-12-31
String category = attributes.getCategory();  // "Travel"
```
#### Integrating Budget with Expense
A key feature to our application is that it can automatically deduct an expense from a budget. 
This integration between two major classes is to allow users to know the status of their budgets 
after adding their expenses. This is provided that
both the expense and the budget are in the same category, and the expense is within the time frame of the budget.

After the expense is added, the constructor will call the BudgetManager to execute the method `deductBudgetFromExpense()`
as illustrated by the sequence diagram below:

It is done by calling a boolean method from `Expense Manager` to check if budget is exceeded or not. 
A warning will be displayed if budget has been exceeded.

```
BudgetManager budgetManager = data.getBudgetManager();
if (budgetManager != null) {
   boolean exceeded = budgetManager.deductBudgetFromExpense(expense);
   if (exceeded) {
       System.out.println("Warning: You have exceeded your budget for category: " + category);
   }
}
```

![DeductBudgetFromExpense](DeductBudgetFromExpense.png)


#### [Future Features] Deleting a Budget 
Deleting a budget is an upcoming feature that is still under development, but it is important to let user
to have the freedom of deleting redundant or no longer used budgets.

---

### Saving

This is an example of the implementation of the Budget and Saving command: `Set Budget`,
which can represent the generic flow of the Budget and Saving management's execution flow.

1. The user inputs the set-budget command.

2. LogicManager (`BudgetGeneralCommand`) passes it to SetBudgetCommandParser.

3. A Budget object is created.

4. SetBudgetCommand is constructed with the budget.

5. Execute(`BudgetList`) adds the budget to the model.

6. The UI reflects the update by printing a success message and the attributes of the `Budget`.


#### General Logic For Budget and Saving in sequence diagram:
![BudgetSavingSequence.png](BudgetSavingSequence.png)

---

### Loan

---
---

### Analytic Command
Here are the implementation of OverviewCommand, one of the four analytic commands, along with a sequence diagram: 
1. **UI.run()**

- The UI class begins listening for user commands in its run() method.

- The user types "analytic", and the UI invokes AnalyticGeneralCommand.handleAnalyticCommand(scanner, analyticsManager).

- This kicks off the “Analytic Mode,” where the system prompts for analytic commands (overview, trend, etc.).

2. **handleAnalyticCommand**

- Inside AnalyticGeneralCommand.handleAnalyticCommand(), the method prints a welcome message ("Analytic Mode: Enter commands...") and loops, continually reading lines from the user.

- When the user types "overview 2025-04", it captures that input for parsing.

3. **Parsing the Command**

- Next, AnalyticGeneralCommand calls AnalyticCommandParser.parseCommand("overview 2025-04").

- AnalyticCommandParser instantiates a new OverviewCommand(4, 2025)

4. **Execution of OverviewCommand**

- After returning the newly created OverviewCommand, handleAnalyticCommand calls overviewCommand.execute(analyticsManager).

- In execute(), the command calls method getMonthlySummary(month, year) from AnalyticManager.

5. **Analytics Logic**

- AnalyticsManager fetches relevant data—like total income, total expenses, and net savings—for April 2025 via its helper (AnalyticDataLoader).

- It assembles a final summary string, including comparisons to March 2025, and returns it to OverviewCommand.

6. **User Output**

- Finally, OverviewCommand prints the returned summary string to the console, displaying the monthly overview to the user.

![OverviewCommandSequence.png](OverviewCommandSequence.png)


---
## Appendix A: Product Scope

### Target user profile

- CLI users who prefer keyboard-based interactions.
- Budget-conscious individuals tracking daily spending.
- Students, young adults or working professionals managing personal finances.

### Value proposition

- Easy and fast, keyboard-based way of recording transactions.
- No setup or signup — works locally and offline.
- Lightweight and highly customizable.
- Comprehensive personal finance toolings including Expense & Income Manger, Saving & Budget Manager, Loan Manager and Analytics Manager.

## Appendix B: User Stories

| Priority | As a ... | I want to ... | So that I can ... |
|----------|-----------|----------------|-------------------|
| High | User | Add expenses | Track spending |
| High | User | Edit/delete expenses | Fix mistakes |
| Medium | User | Sort expenses | View spending trends |
| Medium | User | See top category | Analyze major expenses |
| High | User | Add income sources | Record earnings |
| Medium | User | Manage budgets | Stay within limits |
| Medium | User | Save for goals | Reach financial milestones |
| Low | User | Track loanManager | Manage borrowings and lending |
| High     | User | See monthly financial summary  | Manage my finances better                      |
| Medium   | User | See financial trends over time | Understand my financial habits and plan wisely |
| Medium   | User | See spending insights          | Make wiser spending decisions                  |
| Medium   | User | See spending breakdown         | Manage my budget better                         |

---

## Appendix C: Non-Functional Requirements

- Should work on Windows, MacOS, and Linux with Java 17+.
- Must handle 100+ records without performance drop.
- CLI should respond within 1 second per command.

---

## Appendix D: Glossary

- **CLI**: Command Line Interface. A text-based interface where users interact with the program by typing commands.
- **Index**: The number shown when listing items; used to refer to entries in a list (1-based for user, 0-based in code).
- **IOHandler**: A utility class that handles all input/output operations. Abstracts away calls to `System.out` and `System.err` for better modularity and testability.
- **Expense**: Money spent by the user on items such as food, transport, or utilities.
- **Income**: Money received by the user from various sources like salary, gifts, or investments.
- **Budget**: A financial constraint defined by the user for a particular category, with a total amount and deadline.
- **Loan**: Money lent or borrowed, optionally with interest applied over time.
- **Saving**: A goal the user is saving money towards, tracked by name, amount, and deadline.
- **Category**: A label to classify a transaction (e.g., "Food", "Travel", "Salary", "Entertainment").
- **Command Pattern**: A software design pattern where each operation is encapsulated in a separate object that implements a common interface (e.g., `execute()` method).
- **Parser**: A class responsible for converting raw user input into structured data or command objects. Different parsers exist for each module (e.g., `BudgetParser`, `ExpenseCommandParser`).
- **Attribute class**: A utility class (e.g., `BudgetAttributes`) that extracts fields from user input using identifiers like `n/` (name), `a/` (amount), `e/` (end date).
- **Identifier**: A prefix used in command input strings to label arguments. For example:
    - `n/` = name
    - `a/` = amount
    - `e/` = end date
    - `c/` = category
    - `i/` = index
- **Money**: A class representing an amount of currency with support for precision and arithmetic operations.
- **Finance**: An abstract class extended by financial entities like `Budget`, `Income`, or `Expense`.
- **Interest**: A class used by advanced `Loan` types to apply and calculate interest over time.
- **Command**: A base class/interface for all user actions (e.g., `AddExpenseCommand`, `DeleteBudgetCommand`). Each command overrides `execute(...)`.
- **Mode**: A context in which the user interacts with a specific feature (e.g., "expense mode", "budget mode"). Each mode has its own set of valid commands.


---

## Appendix E: Instructions for Manual Testing

### Expense Module

1. Run `expense` to enter expense mode.
2. Try:
   ```
   add Lunch 10 Food 2025-04-01
   list
   edit 1 Dinner 20 Food
   delete 1
   sort recent
   ```

### Income Module

1. Run `income` mode.
2. Try:
   ```
   add Salary 3000 Job 2025-04-01
   edit 1 Bonus 500 Job
   list
   delete 1
   ```

### Budget Module
1. Run `budget` to enter budget mode from the main menu.
2. Try:
```
set n/Trip a/1000 e/2025-12-31 c/Travel
check i/1
list
deduct i/1 a/200
modify i/1 [n/Holiday] [a/300] [e/2025-10-10] [c/Trip]
```
**Note**: the attributes in `[ ]` are optional to include. If not included, the program assumes
the attribute is not modified. For example, `modify i/1 a/500 c/Trip` only modifies `amount` and `category`,
but does not modify `name` and `endDate`.

### Saving Module
1. Run `saving` to enter budget mode from the main menu.
2. Try:
```
set n/Laptop a/2000 b/2025-10-01
contribute i/1 a/500
list
delete-s i/1
delete-c i/1 c/1
```

### Loan Module

```
loan
add
list
show 1
edit 1 description
find John outgoing loan
delete 1
```
### Analytic Module
1. Run `analytic` to enter analytic mode from the main menu.
2. Try:
```
overview 2025-03                                   
trend income 2025-04-01 2025-05-02 weekly
insight                            
spending-breakdown 2024-12                                 
```
---

## Acknowledgements

- This project reused some ideas and interfaces from the [AddressBook-Level3](https://github.com/se-edu/addressbook-level3) project.
- Structure and format of the Developer Guide closely follow AB3’s conventions.

