# User Guide

## Introduction

**CashFlow** is a personal finance tracking application that helps you manage your **expenses**, **incomes**, **budgets**, **loans**, and **savings** — all in one place. Designed for individuals who want to stay in control of their finances, CashFlow runs in a simple **command-line interface** and supports key features such as:

- Adding, editing, and listing expenses and incomes
- Adding, editing, and listing budgets and savings
- Adding, editing, and listing loans
- Analysing all these trends for better decision makings

CashFlow is ideal for students and working professionals who want a lightweight, intuitive tool to stay financially organized. No fancy UI needed — just meaningful commands that get the job done.

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


## Expense Management Commands

The Expense commands will be available when entering **expense mode**:
```
expense
```

Use help to see the list of available commands:
```
help
```

Track your daily spending using the following commands:

### Add Expense

**Command:**
```
add <description> <amount> <category> [yyyy-mm-dd]
```

- `description`: What the expense is for (e.g., Lunch)
- `amount`: Expense amount in your preferred currency (e.g., 15.50)
- `category`: A label for grouping expenses (e.g., Food)
- `yyyy-mm-dd` *(optional)*: Date of the expense. Defaults to today's date if omitted.

**Example:**
```
add Lunch 12.5 Food 2025-04-01
```

---

### Edit Expense

**Command:**
```
edit <index> <newDescription> <newAmount> <newCategory> [yyyy-mm-dd]
```

- `index`: The number of the expense you want to edit
- `newDescription`: Updated description
- `newAmount`: Updated amount
- `newCategory`: Updated category
- `yyyy-mm-dd` *(optional)*: Updated date. Defaults to today if omitted.

**Example:**
```
edit 1 Dinner 18.0 Food 2025-04-02
```

---

### Delete Expense

**Command:**
```
delete <index>
```

- `index`: The number of the expense you want to delete

**Example:**
```
delete 3
```

---

### List Expenses

**Command:**
```
list
```

Displays all recorded expenses.

---

### List Expenses by Category

**Command:**
```
list category <category>
```

- `category`: The category to filter by

**Example:**
```
list category Transport
```

---

### Sort Expenses by Date

**Command (most recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### Show Top/Bottom Spending Category

**Command:**
```
top
```

Displays the category with the highest total spending.

**Command:**
```
bottom
```

Displays the category with the lowest total spending.

---

## Income Management Commands

The Income commands will be available when entering **income mode**:
```
income
```

Use help to see the list of available commands:
```
help
```

Track all income sources and financial gains with the commands below:

### Add Income

**Command:**
```
add <source> <amount> <category> [yyyy-mm-dd]
```

- `source`: Where the income is from (e.g., Salary)
- `amount`: Income amount in your preferred currency (e.g., 3000)
- `category`: Group label (e.g., Job, Freelance)
- `yyyy-mm-dd` *(optional)*: Date of the income. Defaults to today if omitted.

**Example:**
```
add Salary 2500 Job 2025-03-31
```

---

### Edit Income

**Command:**
```
edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]
```

- `index`: The number of the income you want to edit
- `newSource`: Updated income source
- `newAmount`: Updated amount
- `newCategory`: Updated category
- `yyyy-mm-dd` *(optional)*: Updated date

**Example:**
```
edit 1 Bonus 500 Job 2025-04-01
```

---

### Delete Income

**Command:**
```
delete <index>
```

- `index`: The number of the income to delete

**Example:**
```
delete 2
```

---

### List Incomes

**Command:**
```
list
```

Lists all recorded incomes.

---

### List Incomes by Category

**Command:**
```
list category <category>
```

- `category`: The category to filter by

**Example:**
```
list category Freelance
```

---

### Sort Incomes by Date

**Command (most recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### Show Top/Bottom Income Category

**Command:**
```
top
```

Displays the category with the highest income total.

**Command:**
```
bottom
```

Displays the category with the lowest income total.


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

### List all loans

**Command:** `list`

**Description:** Displays a list of all loans. Only basic information (lender, borrower, balance and return status) is shown.

**Example:** `list`
```
Here is the list of all loans:

```
### Show the details of a specific loan

**Command:** `show X`

**Description:** Shows every detail of the Xth loan in the list. Each detail is shown in a separated line.

**Example:** `show 1`
```


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

## Coming Soon

- Recurring entries (e.g., monthly salary)
- Exporting to CSV
- Search by keyword or date range
- Gives a saving summary upon completing a saving goal
- Give user the freedom to filter the Budgets/Savings to list out based on their active status
