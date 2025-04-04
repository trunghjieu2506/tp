# User Guide

## Introduction

{Give a product intro}

## Quick Start

1. Ensure you have **Java 17 or above** installed.
2. Download the latest version of the app from the [Releases](https://github.com/AY2425S2-CS2113-W11-2/tp.git) page.
3. Open a terminal in the folder where the `.jar` file is located.
4. Run the application using the command:
   ```
   java -jar tp.jar
   ```
5. Start entering commands (e.g., `help`) and press Enter to use the app.

## Features

### 📥 Adding an Expense

**Command:**
```
add <desc> <amount> <category> [yyyy-mm-dd]
```

- `desc`: Description (e.g., Lunch)
- `amount`: Positive number (e.g., 15.50)
- `category`: Any word (e.g., Food, Transport)
- `date` (optional): Defaults to today's date if omitted

**Example:**
```
add Lunch 15.5 Food 2025-03-31
```

---

### ✏️ Editing an Expense

**Command:**
```
edit <index> <newDesc> <newAmount> <newCategory> [yyyy-mm-dd]
```

**Example:**
```
edit 2 Dinner 18.0 Food 2025-04-01
```

---

### ❌ Deleting an Expense

**Command:**
```
delete <index>
```

**Example:**
```
delete 3
```

---

### 📋 Listing Expenses

**Command:**
```
list
```

Displays all recorded expenses.

---

### 📂 Listing Expenses by Category

**Command:**
```
list category <categoryName>
```

**Example:**
```
list category Food
```

---

### 🔃 Sorting Expenses by Date

**Command (most recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### 📊 Top/Bottom Expense Category

**Top spending category:**
```
top
```

**Bottom spending category:**
```
bottom
```

---

### 📈 Adding an Income

**Command:**
```
add <source> <amount> <category> [yyyy-mm-dd]
```

**Example:**
```
add Salary 2500 Job 2025-03-31
```

---

### ✏️ Editing an Income

**Command:**
```
edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]
```

**Example:**
```
edit 1 Bonus 500 Job
```

---

### ❌ Deleting an Income

**Command:**
```
delete <index>
```

---

### 📋 Listing Income

**Command:**
```
list
```

---

### 📂 Listing Income by Category

**Command:**
```
list category <categoryName>
```

---

### 🔃 Sorting Income by Date

**Command (most recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### 📊 Top/Bottom Income Category

**Top income category:**
```
top
```

**Bottom income category:**
```
bottom
```

---

### 🆘 Help

**Command:**
```
help
```

Shows a list of available commands and their usage.

## Budget Management Commands

The Budget commands will be available when entering budget mode: ```budget```

Manage your budgets alongside your expenses and incomes with the following commands:

### Set Budget

**Command:** ```set n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY```

- `NAME`: The name for your budget.
- `AMOUNT`: The total budget amount.
- `YYYY-MM-DD`: The end date for the budget period.
- `CATEGORY`: The category for this budget.

**Example:** set n/Entertainment a/500 e/2025-12-31 c/Leisure

---

### List Budget

**Command:** ```list```
- Lists out all the budgets available
---

### Check Budget

**Command:** ```check i/INDEX```

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget

**Example**:  ```check i/1```

---

### Add to Budget

**Command:** add i/INDEX a/AMOUNT

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget
- `AMOUNT`: The amount of budget to be added

**Example**:  ```add i/1 a/500```
- this adds $500 to budget with index 1, on top of the current budget limit

### Deduct from Budget

**Command:** deduct i/INDEX a/AMOUNT

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget
- `AMOUNT`: The amount of budget to be deducted

**Example**:  ```deduct i/1 a/500```
- this deducts $500 to budget with index 1, so the new remaining budget will be last_remaining_budget - 500

### Modify Budget

**Command:** ```i/INDEX n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY```

**You can skip any of the attributes if you are not changing them, but must include the index**

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget
- `NAME`: Name of the budget to modify to
- `AMOUNT`: The amount of budget to be modified to
- `YYYY-MM-DD`: The deadline to be modified
- `CATEGORY`: The category you want to change the budget to

**Example**:  
- ```modify i/1 n/trip a/5000 e/2025-04-30 c/trips```
- ```modify i/1 n/trip c/trip123```
- ```modify i/1 a/5000```
- this adds $500 to budget with index 1, on top of the current budget limit


## Saving Management Commands

The Saving commands will be available when entering saving mode: ```saving```

Manage your budgets alongside your expenses and incomes with the following commands:

### Set Saving

**Command:** ```n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD```

- `GOAL_NAME`: The name for your budget.
- `AMOUNT`: The total budget amount.
- `YYYY-MM-DD`: The end date for the budget period.

**Example:** set n/Entertainment a/500 e/2025-12-31
- This sets a new saving of $500 to be completed by 31 Dec 2025

---

### List Saving

**Command:** ```list```
- Lists out all the savings
---

### Add Contribution to Saving

**Command:** ```contribute i/INDEX a/AMOUNT```

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget
- `AMOUNT`: The amount of budget to be added

**Example**:  ```contribute i/1 a/500```
- this adds $500 to the saving with index 1, now the contribution is last_contribution + 500

---

### Loan Mode

**Command:** `loan`

**Description:** All commands in this section run in the loan mode. To enter loan mode, simply enter this command.

**Example:** `loan`
```
Enter command (type 'help' for commands): loan
Loan Mode: Enter commands (type 'exit' to return)
> 
```

### List all loans

**Command:** `list`

**Description:** Displays a list of all loans. Only basic information (lender, borrower, balance and return status) is shown.

**Example:** `list`
```
> list
Here are all recorded loans:
[1] Lender: [lender 1]    Borrower: [borrower 1]    Amount: USD 100.00
    Not Returned
[2] Lender: [lender 2]    Borrower: [borrower 1]    Amount: USD 500.00    Start Date: 2025-04-03
    Interest: Compound Interest 5.0% Per 1 Month
    Outstanding Balance: USD 500.00
    Not Returned
```
### Show the details of a specific loan

**Command:** `show X`

**Description:** Shows every detail of the Xth loan in the list. Each detail is shown in a separated line.

**Example:** `show 1`
```
> show 1
[1] Lender: lender 1
    Borrower: borrower 1
    Amount: USD 100.00
    Start Date: None
    Return Date: None
    Description: This is a simple bullet loan.
    Tags: None
    Not Returned
```

### Adding a simple bullet loan

**Input sequence:** as shown in the code example

**Description:** Adds a simple bullet loan to the list, with optional description and return dates.

**Example:** `add`
```
> add
With or without interest? (y/n)
> n
Enter the lender's name:
> lender 1
Enter the borrower's name:
> borrower 1
Key in the amount of money lent:
> 100
Key in the description (Key in "N/A" if not applicable):
> N/A
Key in the return date of the loan (yyyy-mm-dd) (Key in "N/A" if not applicable):
> N/A
Simple Bullet Loan added: Lender: [lender 1]    Borrower: [borrower 1]    Amount: USD 100.00
    Not Returned
```
### Adding an advanced loan

**Input sequence:** as shown in the code example

**Description:** Adds an advanced loan to the list, with compulsory start date, optional description and return dates.

**Example:** `add`
```
> add
With or without interest? (y/n)
> y
Enter the lender's name:
> lender 2
Enter the borrower's name:
> borrower 1
Key in the amount of principal:
> 500
Key in the start date of the loan (yyyy-mm-dd):
> 2025-04-03
Key in the return date of the loan (yyyy-mm-dd) (Key in "N/A" if not applicable):
> N/A
Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):
> COMPOUND 5% per month
Key in the description (Key in "N/A" if not applicable):
> This is an advanced loan.
Advanced Loan added: Lender: [lender 2]    Borrower: [borrower 1]    Amount: USD 500.00    Start Date: 2025-04-03
    Interest: Compound Interest 5.0% Per 1 Month
    Outstanding Balance: USD 500.00
    Not Returned
```

### Editing an attribute of a loan

**Command:** `edit X [attribute]`

**Description:** Edit one attribute of a loan. (The lender and borrower cannot be edited)

**Example:** `edit 1 description`
```
> edit 1 description
Key in the new description:
> This is a simple bullet loan.
The description of the following loan is updated:
Lender: lender 1
Borrower: borrower 1
Amount: USD 100.00
Start Date: None
Return Date: None
Description: This is a simple bullet loan.
Tags: None
Not Returned
```
### Finding outgoing loans from someone

**Command:** `find [name] outgoing loan` or `find outgoing loan [name]`

**Description:** Finds all loans lent by the input name.

**Example:** `find lender 1 outgoing loan`
```
> find lender 1 outgoing loan
Outgoing loans for [lender 1] is:
[1] Lender: [lender 1]    Borrower: [borrower 1]    Amount: USD 100.00
    Not Returned
```
### Finding incoming loans from someone

**Command:** `find [name] incoming loan` or `find incoming loan [name]`

**Description:** Finds all loans borrowed by the input name.

**Example:** `find borrower 1 incoming loan`
```
> find borrower 1 incoming loan
Incoming loans for [borrower 1] are:
[1] Lender: [lender 1]    Borrower: [borrower 1]    Amount: USD 100.00
    Not Returned
[2] Lender: [lender 2]    Borrower: [borrower 1]    Amount: USD 500.00    Start Date: 2025-04-03
    Interest: Compound Interest 5.0% Per 1 Month
    Outstanding Balance: USD 500.00
    Not Returned
```
### Deleting loans

**Command:** `delete X`

**Description:** Deletes the Xth loan from the list.

**Example:** `delete 1`
```
> delete 1
Successfully deleted the following loan:
Lender: [lender 1]    Borrower: [borrower 1]    Amount: USD 100.00
    Not Returned
```


## FAQ

**Q:** What happens if I forget to type the date?  
**A:** The system automatically uses today’s date.

**Q:** Can I use lowercase for category names?  
**A:** Yes. The system normalizes category casing.

**Q:** Do I need to follow the date formate exactly in Budget and Saving managements?
**A:** Yes! Please use the exact date format `YYYY-MM-DD`

## Command Summary

### Expenses and Incomes

| Feature       | Command Format |
|---------------|----------------|
| Add Expense   | `add <desc> <amount> <category> [date]` |
| Edit Expense  | `edit <index> <newDesc> <newAmount> <newCategory> [date]` |
| Delete Expense| `delete <index>` |
| List Expenses | `list` |
| List by Category | `list category <name>` |
| Sort by Date  | `sort recent` / `sort oldest` |
| Top Category  | `top` |
| Bottom Category | `bottom` |
| Add Income    | `add <source> <amount> <category> [date]` |
| Edit Income   | `edit <index> <newSource> <newAmount> <newCategory> [date]` |
| Delete Income | `delete <index>` |
| List Income   | `list` |
| Help          | `help` |

### Budgets and Savings

| Feature            | Command Format                                               |
|--------------------|--------------------------------------------------------------|
| Set Budget         | `set n/BUDGET_NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY`           |
| Check Budget       | `check i/INDEX`                                              |
| List Budgets       | `list`                                                       |
| Deduct from Budget | `deduct i/INDEX a/AMOUNT`                                      |
| Add to Budget      | `add i/INDEX a/AMOUNT`                                         |
| Modify Budget      | `modify i/INDEX n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY`         |
| Set Saving Goal      | `set n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD`           |
| Contribute to Saving | `contribute i/INDEX a/AMOUNT`                     |
| List Saving Goals    | `list`                                           |

### Loans

| Feature               | Command Format       |
|-----------------------|----------------------|
| Add Loan              | `add`                |
| Edit Loan attribute   | `edit X [attribute]` |
| List Loans            | `list`               |
| Delete Loan           | `delete X`           |
| Show Loan Details     | `show X`             |
| Find associated loans | `find [name]`        |

## Coming Soon

- Recurring entries (e.g., monthly salary)
- Exporting to CSV
- Search by keyword or date range
- Gives a saving summary upon completing a saving goal
- Give user the freedom to filter the Budgets/Savings to list out based on their active status
