# User Guide

## Introduction

**CashFlow** is a personal finance tracking application that helps you manage your **expenses**, **incomes**, **budgets**, **loans**, and **savings** — all in one place. Designed for individuals who want to stay in control of their finances, CashFlow runs in a simple **command-line interface** and supports key features, but not limited to, such as:

- Adding, editing, and listing expenses and incomes
- Adding, editing, and listing budgets and savings
- Adding, editing, and listing loans
- Receiving analytics reports such income trends, spending breakdown and more.

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

After entering **expense mode**, use help to see the list of available commands:
```
help
```

Track your daily spending using the following commands:

### Add Expense
Adds a new expense entry with a description, amount, category, and optional date.

**Command:**
```
add <description> <amount> <category> [yyyy-mm-dd]
```

- `description`: What the expense is for (e.g. Lunch)
- `amount`: Expense amount in your preferred currency (e.g. 15.50)
- `category`: A label for grouping expenses (e.g. Food)
- `yyyy-mm-dd` *(optional)*: Date of the expense. Defaults to today's date if omitted.

**Example:** `add Lunch 12.5 Food 2025-04-01`

```
> add Lunch 12.5 Food 2025-04-01
Added: Lunch - USD 12.50 on 2025-04-01 [Category: Food]
```
---

### Edit Expense
Updates details of an existing expense.

**Command:**
```
edit <index> <newDescription> <newAmount> <newCategory> [yyyy-mm-dd]
```

- `index`: The number of the expense you want to edit
- `newDescription`: Updated description
- `newAmount`: Updated amount
- `newCategory`: Updated category
- `yyyy-mm-dd` *(optional)*: Updated date. Defaults to today if omitted.

**Example:** `edit 1 Dinner 18.0 Food 2025-04-02`
```
> edit 1 Dinner 18.0 Food 2025-04-02
Updated: Dinner - USD 18.00 on 2025-04-02 [Category: Food]
```

---

### Delete Expense
Removes an expense by its list index.

**Command:**
```
delete <index>
```

- `index`: The number of the expense you want to delete

**Example:** `delete 1`
```
> delete 1
Deleted: Dinner - USD 18.00 on 2025-04-02 [Category: Food]
```

---

### List Expenses
Displays all recorded expenses.

**Command:**
```
list
```

---

### List Expenses by Category
Filters expenses by a specified category.

**Command:**
```
list category <category>
```

- `category`: The category to filter by

**Example:** `list category Transport`
```
> list category Transport
Expenses in category: Transport
1. Bus - USD 2.00 on 2025-04-06 [Category: Transport]
2. Train - USD 4.00 on 2025-04-06 [Category: Transport]
```

---

### Sort Expenses by Date
Orders expenses either by most recent or oldest date.

**Command (recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### Top/Bottom Spending Category
Identifies the category with the highest or lowest spending.

**Command (highest spending):**

```
top
```

**Command (lowest spending):**

```
bottom
```

---

## Income Management Commands

The Income commands will be available when entering **income mode**:
```
income
```

After entering **income mode**, use help to see the list of available commands:
```
help
```

Track all income sources with the commands below:

### Add Income
Adds a new income entry with a description, amount, category, and optional date.

**Command:**
```
add <source> <amount> <category> [yyyy-mm-dd]
```

- `source`: Where the income is from (e.g., Salary)
- `amount`: Income amount in your preferred currency (e.g., 3000)
- `category`: Group label (e.g., Job, Freelance)
- `yyyy-mm-dd` *(optional)*: Date of the income. Defaults to today if omitted.

**Example:** `add Salary 2500 Job 2025-03-31`
```
> add Salary 2500 Job 2025-03-31
Added: Salary - USD 2500.00 on 2025-03-31 [Category: Job]
```

---

### Edit Income
Updates details of an existing income.

**Command:**
```
edit <index> <newSource> <newAmount> <newCategory> [yyyy-mm-dd]
```

- `index`: The number of the income you want to edit
- `newSource`: Updated income source
- `newAmount`: Updated amount
- `newCategory`: Updated category
- `yyyy-mm-dd` *(optional)*: Updated date

**Example:** `edit 1 Bonus 500 Job 2025-04-01`
```
> edit 1 Bonus 500 Job 2025-04-01
Updated: Bonus - USD 500.00 on 2025-04-01 [Category: Job]
```

---

### Delete Income
Removes an income by its list index.

**Command:**
```
delete <index>
```

- `index`: The number of the income to delete

**Example:** `delete 1`
```
> delete 1
Deleted: Bonus - USD 500.00 on 2025-04-01 [Category: Job]
```

---

### List Incomes
Displays all recorded incomes.

**Command:**
```
list
```

---

### List Incomes by Category
Filters incomes by a specified category.

**Command:**
```
list category <category>
```

- `category`: The category to filter by

**Example:** `list category Job`
```
> list category Job
Incomes in category: Job
1. Bonus - USD 500.00 on 2025-04-01 [Category: Job]
2. Salary - USD 2500.00 on 2025-03-31 [Category: Job]
```

---

### Sort Incomes by Date
Orders incomes either by most recent or oldest.

**Command (recent first):**
```
sort recent
```

**Command (oldest first):**
```
sort oldest
```

---

### Show Top/Bottom Income Category
Identifies the category with the highest or lowest spending.

**Command (highest spending):**
```
top
```

**Command (lowest spending):**
```
bottom
```

---
## Budget Management Commands

**Important note**: (v2.1) budget category is no longer case sensitive, but others are.

The Budget commands will be available when entering budget mode.

```
budget
```

Manage your budgets alongside your expenses and incomes with the following commands:

### Set Budget

**Command:** 

```
set n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY
```

- `NAME`: The name for your budget.
- `AMOUNT`: The total budget amount.
- `YYYY-MM-DD`: The end date for the budget period.
- `CATEGORY`: The category for this budget.

**Example:** 
```
set n/Entertainment a/500 e/2025-12-31 c/Leisure
```

---

### List Budget
Lists out all the budgets available

**Command:** 
```
list
```

---

### Check Budget
This command checks on a specific budget, and print out all the details, including all attributes,
and also its expense history

**Command:** 
```
check i/INDEX
```

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget

**Example**:  
```
check i/1
```

---


### Deduct from Budget

**Command:** 
```
deduct i/INDEX a/AMOUNT
```

- `INDEX`: The index of the budget you want to check. You can get the index by listing the budget
- `AMOUNT`: The amount of budget to be deducted

**Example**:  
```
deduct i/1 a/500
```
- This deducts $500 to budget with index 1, so the new remaining budget will be last_remaining_budget - 500

### Modify Budget
(v2.1 changes): Merged Add Budget command into modify budget, as the modify command can also handle add budget command.

**Command:** 
```
i/INDEX n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY
```

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
- This adds $500 to budget with index 1, on top of the current budget limit

**Note**: changing the budget category will remove all its expenses. So do it wisely!


## Saving Management Commands

The Saving commands will be available when entering saving mode: 
```
saving
```

Manage your savings through these commands:

### Set Saving

**Command:** 
```
n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD
```

- `GOAL_NAME`: The name for your saving.
- `AMOUNT`: The total saving amount.
- `YYYY-MM-DD`: The deadline for the saving period.

**Example:** 
```
set n/Entertainment a/500 b/2025-12-31
```
- This sets a new saving of $500 to be completed by 31 Dec 2025

---

### List Saving
Lists out all the savings

**Command:** 
```
list
```
---

### Add Contribution to Saving

**Command:** 
```
contribute i/INDEX a/AMOUNT
```

- `INDEX`: The index of the saving you want to contribute to. You can get the index by listing the savings
- `AMOUNT`: The amount of saving to be added

**Example**:  
```
contribute i/1 a/500
```
- This adds $500 to the saving with index 1, now the contribution is last_contribution + 500

---

### Check on a Saving
**Command**: 
```
check i/INDEX
```

- `INDEX`: The index of the saving you want to check. You can get the index by listing the savings.

**Example**: check i/1 

### Delete a Saving

**Command:**
```
delete-s i/INDEX
```

- `INDEX`: The index of the saving you want to delete. You can get the index by listing the savings.

**Example**:
```
delete-s i/1
```
- This deletes the saving of index 1. Note that the rest of the savings will be shifted after deletion.
- ```
  Example:
  
  list
  
  Saving 1. [ACTIVE] {Name: saving1, Goal: USD 100.00, Current Progress: USD 0.00, By: 2025-04-30}
  Saving 2. [ACTIVE] {Name: saving2, Goal: USD 100.00, Current Progress: USD 0.00, By: 2025-04-30}
  
  delete-s i/1
  list
  
  Saving 1. [ACTIVE] {Name: saving2, Goal: USD 100.00, Current Progress: USD 0.00, By: 2025-04-30}
  ```

---

### Delete a Contribution

**Command:**
```
delete-c i/INDEX_S c/INDEX_C
```

- `INDEX_S`: The index of the saving you want to check on. You can get the index by listing the savings.
- `INDEX_C`: The index of the contribution you want to delete. You can get the index by checking the saving.

**Example**:
```
delete-c i/1 c/1
```
- This deletes the 2nd contribution of saving index 1. 
- Note that the rest of the contribution will be shifted after deletion, just like deleting a saving.

---

### Budget Expense Integration

This feature allows expenses added to be deducted from budget automatically. The deduction is based on the same category set by user.

**Example of not exceeding budget:** 
```
> set n/Food a/200 e/2025-12-12 c/Food
> add cookie 3 food
Budget deducted: [ACTIVE][HAS_REMAINING_BUDGET]{Name: Food, Category: Food, RemainingBudget: USD 197.00, From 2025-04-06 to 2025-12-12}
Added: cookie - USD 3.00 on 2025-04-06 [Category: Food]
```

When the expenses added exceeds the budget set, a warning message will be printed to let the users know.

**Example of exceeding budget (continued):**
```
> add Fine-Dining 200 Food
Budget deducted: [ACTIVE][EXCEEDED_BUDGET]{Name: Food, Category: Food, RemainingBudget: USD -3.00, From 2025-04-06 to 2025-12-12}
Warning: You have exceeded your budget for category: Food
Added: Fine-Dining - USD 200.00 on 2025-04-06 [Category: Food]
```

**Note**: Calculation of available budget left after expense is added only works for existing budget. It does not account for new budget set after the expenses are already recorded.

---
### Loan Management Commands

The Loan commands will be available when entering loan mode by this command:
```
loan
```

### Help
Displays available loan commands and how to use them.

**Command:**
```
help
```

**Example:** `help`
```
> help
Here's a list of loan commands:
----------------------------------------------------------------------
- "add": add a loan.
- "list": view the list of all loanManager recorded.
- "show X": show the details of the Xth loan in the list.
- "edit X [attribute]": edit the specified attribute of the Xth loan.
- "delete X": delete the Xth loan.
- "return X": set the Xth loan as returned.
- "unreturn X": set the Xth loan as not returned.
- "find": find loanManager. Type "help find" for more details.
- "exit": Exit loan program.
----------------------------------------------------------------------
```

### List all loans
Displays a list of all loanManager. Only basic information (lender, borrower, balance and return status) is shown.

**Command:** 
```
list
```

**Example:** `list`
```
> list
Here are all recorded loanManager:
[1] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Principal: USD 100.00    Interest: Compound Interest 3.0% Per 1 Year
    Start Date: 2023-01-01    Return Date: 2025-01-01
    Outstanding Balance: USD 106.09    Not Returned
```
### Show the details of a specific loan
Shows every detail of the Xth loan in the list. Each detail is shown in a separated line.

**Command:** 
```
show X
```

**Example:** `show 2`
```
> show 2
[2] Lender: Qiaozi (Contact Number: 12345678) (E-Mail: someone@example.com)
    Borrower: George
    Amount: USD 100.00
    Start Date: 2023-01-01
    Return Date: 2025-01-01
    Description: None
    Tags: food
    Interest: Compound Interest 3.0% Per 1 Year
    Outstanding Balance: USD 106.09
    Not Returned
```

### Adding a simple bullet loan
Adds a simple bullet loan to the list, with optional description and return dates.

**Input sequence:** as shown in the code example

**Example:** `add`
```
> add
With or without interest? (y/n)
> n
Enter the lender's name:
> George
Enter the borrower's name:
> Qiaozi
Key in the amount of money lent:
> 500
Key in the description (Key in "N/A" if not applicable):
> N/A
Key in the start date of the loan (yyyy-mm-dd) (Key in "N/A" if not applicable):
> N/A
Key in the return date of the loan (yyyy-mm-dd) (Key in "N/A" if not applicable):
> 2025-10-01
Key in a tag (Key in "N/A" to finish):
> food
Added tag: [food]
Key in a tag (Key in "N/A" to finish):
> N/A
Tasks saved successfully!
Simple Bullet Loan added: Lender: [George]    Borrower: [Qiaozi]
    Amount: USD 500.00    Not Returned
```
### Adding an advanced bullet loan
Adds an advanced bullet loan to the list, with compulsory start date, optional description and return dates.

**Input sequence:** as shown in the code example

**Example:** `add`
```
> add
With or without interest? (y/n)
> y
Enter the lender's name:
> George
Enter the borrower's name:
> John
Key in the amount of principal:
> 1000
Key in the start date of the loan (yyyy-mm-dd):
> 2025-04-01
Key in the return date of the loan (yyyy-mm-dd) (Key in "N/A" if not applicable):
> 2026-04-01
Enter the interest (format: [SIMPLE/COMPOUND] [rate] per [X Years/Months/Days]):
> COMPOUND 3% per month
Key in the description (Key in "N/A" if not applicable):
> Toy
Key in a tag (Key in "N/A" to finish):
> food
Added tag: [food]
Key in a tag (Key in "N/A" to finish):
> N/A
Tasks saved successfully!
Advanced Bullet Loan added: Lender: [George]    Borrower: [John]
    Principal: USD 1000.00    Interest: Compound Interest 3.0% Per 1 Month
    Start Date: 2025-04-01    Return Date: 2026-04-01
    Outstanding Balance: USD 1000.00    Not Returned
```

### Editing an attribute of a loan
Edit one attribute of a loan. (The lender and borrower cannot be edited)

**Command:** 
```
edit X [attribute]
```

**Example:** `edit 1 description`
```
> edit 1 description
Key in the new description:
> New description
Tasks saved successfully!
The description of the following loan is updated:
Lender: Qiaozi (Contact Number: 12345678) (E-Mail: someone@example.com)
Borrower: George
Amount: USD 500.00
Start Date: 2023-08-08
Return Date: 2025-01-01
Description: New description
Tags: None
Not Returned
```

### Finding outgoing loans
Finds all loans lent by the user.

**Command:**
```
find outgoing loan
```

**Example:** `find outgoing loan`
```
> find outgoing loan
Your outgoing loans are:
[1] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Principal: USD 100.00    Interest: Compound Interest 3.0% Per 1 Year
    Start Date: 2023-01-01    Return Date: 2025-01-01
    Outstanding Balance: USD 106.09    Not Returned
```

### Finding outgoing loans from someone

Finds all loans lent by the input name.

**Command:** 
```
find [name] outgoing loan` or find outgoing loan [name]
```

**Example:** `find George outgoing loan`
```
> find George outgoing loan
Outgoing loans for [George] are:
[1] Lender: [George]    Borrower: [Qiaozi]
    Amount: USD 500.00    Not Returned
[2] Lender: [George]    Borrower: [John]
    Principal: USD 1000.00    Interest: Compound Interest 3.0% Per 1 Month
    Start Date: 2025-04-01    Return Date: 2026-04-01
    Outstanding Balance: USD 1000.00    Not Returned
```

### Finding incoming loans
Finds all loans borrowed by the user.

**Command:** 
```
find incoming loan
```
**Example:**`find incoming loan`

```
> find incoming loan
Your incoming loan is:
[1] Lender: [George]    Borrower: [Qiaozi]
    Amount: USD 500.00    Not Returned
```

### Finding incoming loans from someone
Finds all loans borrowed by the input name.

**Command:** 
```
find [name] incoming loan` or `find incoming loan [name]
```

**Example:** `find George incoming loan`
```
> find George incoming loan
Incoming loans for [George] are:
[1] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Principal: USD 100.00    Interest: Compound Interest 3.0% Per 1 Year
    Start Date: 2023-01-01    Return Date: 2025-01-01
    Outstanding Balance: USD 106.09    Not Returned
```
### Finding overdue loans
Finds all overdue loans.

**Command:**
```
find overdue loan
```
**Example:** `find overdue loan`
```
> find overdue loan
Overdue loans are:
[1] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Principal: USD 100.00    Interest: Compound Interest 3.0% Per 1 Year
    Start Date: 2023-01-01    Return Date: 2025-01-01
    Outstanding Balance: USD 106.09    Not Returned
```

### Finding urgent loans
Finds the top X loans with the earliest return dates.

**Command:**
```
find X urgent loan
```
**Example:** `find 3 urgent loan`
```
> find 3 urgent loan
Here are the top 3 urgent loans
[1] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Principal: USD 100.00    Interest: Compound Interest 3.0% Per 1 Year
    Start Date: 2023-01-01    Return Date: 2025-01-01
    Outstanding Balance: USD 106.09    Not Returned
[3] Lender: [George]    Borrower: [Qiaozi]
    Amount: USD 500.00    Not Returned
```

### Finding largest loans
Finds the top X loans with the highest balance.

**Command:**
```
find X largest loan
```
**Example:** `find 3 largest loan`
```
> find 3 largest loan
Here are the top 3 largest loans:
[1] Lender: [George]    Borrower: [John]
    Principal: USD 1000.00    Interest: Compound Interest 3.0% Per 1 Month
    Start Date: 2025-04-01    Return Date: 2026-04-01
    Outstanding Balance: USD 1000.00    Not Returned
[2] Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
[3] Lender: [George]    Borrower: [Qiaozi]
    Amount: USD 500.00    Not Returned
```

### Deleting Loan
Deletes the Xth loan from the list.

**Command:** 
```
delete X
```

**Example:** `delete 1`
```
> delete 1
Tasks saved successfully!
Successfully deleted the following loan:
Lender: [Qiaozi]    Borrower: [George]
    Amount: USD 500.00    Not Returned
```

### Returning / Unreturning Loan
Return / unreturn the Xth loan from the list.

**Command:**
```
return / unreturn X
```

**Example:** `return 3`
```
> return 3
Tasks saved successfully!
The return status of the following loan is updated:
Lender: George
Borrower: John (Contact Number: 11111111)
Amount: USD 1000.00
Start Date: 2025-04-01
Return Date: 2026-04-01
Description: Toy
Tags: food
Interest: Compound Interest 3.0% Per 1 Month
Outstanding Balance: USD 1000.00
Returned
```



## Analytics Command

The analytics commands will be available when entering **analytic mode**.

**NOTE**: The program currently does not support currency conversion. All analytics generated will use the latest currency configuration and do not take into account currency conversion.

### Display monthly financial summary
Generates monthly summary of total expense, income, savings, and comparison with previous month. Takes in optional month

**Command:**. ```overview [yyyy-mm]```
- `yyyy-mm` *(optional)*: Month of summary. Defaults to current month if omitted.

**Example:**
```
> overview
---------------------------------

Monthly Summary for APRIL 2025:
- Total Income: EUR 1500.0
- Total Expenses: EUR 216.5
- Net Savings (Income - Expense): EUR 1283.5

---------------------------------

Comparison with Last Month (MARCH 2025):
 - Income: EUR 1500.0 vs EUR0.0
 - Expenses: EUR 216.5 vs EUR1173.0

---------------------------------
```
### Display financial trends
Generate bar charts of income/expense trends on a weekly or monthly basis. 

**Command:**. ```trend <data-type> <start-date> <end-date> <interval>```
- `data-type`: Type of data to display. Select between expense and income.
- `start-date`: Start date of the chart. Follow format yyyy-mm-dd.
- `end-date`: End date of the chart. Follow format yyyy-mm-dd.
- `interval`: Interval to display. Select between monthly and weekly. 

**Example:**
```
> trend expense 2025-03-01 2025-04-30 monthly
Trend Over Time for EXPENSE (monthly):
From 2025-03-01 to 2025-04-30
--------------------------------------------
2025-03    | ################################################## (1173.00)
2025-04    | ######### (216.50)
--------------------------------------------
```
System.out.println("  help                                                  - Display available commands and usage");
System.out.println("  overview [yyyy-mm]                                    - Display monthly financial summary");
System.out.println("  trend <data-type> <start-date> <end-date> <interval>  - Display trends from a range");
System.out.println("  insight [yyyy-mm]                                     - Display spending insights");
System.out.println("  exit                                                  - Exit the analytic program");
### Display spending insights
Generates insights on new type of spending and significant changes in spending compared to previous month. 

**Command:**. ```insight [yyyy-mm]```
- `yyyy-mm` *(optional)*: Month of insights. Defaults to current month if omitted.

**Example:**
```
> insight
Spending Insights for 2025-04 vs. 2025-03:
--------------------------------------------------
- New spending in category 'Lifestyle': 111.00 (no spending in this category last month)
- You spent 95.1% less on Food this month than last month. (55.50 vs. 1123.00)
--------------------------------------------------
```
System.out.println("  spending-breakdown [yyyy-mm]                          - Display breakdown of spending");

### Display breakdown of spending
Generates breakdown of monthly spending in terms of spending categories. Display bar charts with percentages for each spending category. 

**Command:**. ```spending-breakdown [yyyy-mm]```
- `yyyy-mm` *(optional)*: Month of spending breakdown report. Defaults to current month if omitted.

**Example:**
```
> spending-breakdown
Spending Breakdown for 2025-04:
--------------------------------------------------
Education       | ######################                             (50.00) [23.1%]
Lifestyle       | ################################################## (111.00) [51.3%]
Food            | #########################                          (55.50) [25.6%]
Grand Total: 216.50
--------------------------------------------------
```

---

## FAQ

**Q:** What happens if I forget to type the date for expense and income?  
**A:** The system automatically uses today’s date for expense and income.

**Q:** Can I use lowercase for category names for expense and income?  
**A:** Yes. The system normalizes category casing for expense and income inputs.

**Q:** Can my desc have multiple words for expense and income?  
**A:** Yes, however do use hyphen '-' to replace the spaces.

**Q:** Do I need to follow the date format exactly in Budget and Saving managements?
**A:** Yes! Please use the exact date format `YYYY-MM-DD`

**Q:** What is the name of the Person in ContactList that represents myself?
**A:** It is the same as your username.

## Command Summary

### Expenses

| Feature           | Command Format                                            |
|-------------------|-----------------------------------------------------------|
| Add Expense       | `add <desc> <amount> <category> [date]`                   |
| Edit Expense      | `edit <index> <newDesc> <newAmount> <newCategory> [date]` |
| Delete Expense    | `delete <index>`                                          |
| List Expenses     | `list`                                                    |
| List by Category  | `list category <name>`                                    |
| Sort by Date      | `sort recent` / `sort oldest`                             |
| Top Category      | `top`                                                     |
| Bottom Category   | `bottom`                                                  |
| List all commands | `help`                                                    |


### Incomes

| Feature           | Command Format                                              |
|-------------------|-------------------------------------------------------------|
| Add Income        | `add <source> <amount> <category> [date]`                   |
| Edit Income       | `edit <index> <newSource> <newAmount> <newCategory> [date]` |
| Delete Income     | `delete <index>`                                            |
| List Income       | `list`                                                      |
| List by Category  | `list category <name>`                                      |
| Sort by Date      | `sort recent` / `sort oldest`                               |
| Top Category      | `top`                                                       |
| Bottom Category   | `bottom`                                                    |
| List all commands | `help`                                                      |


### Budget

| Feature            | Command Format                                           |
|--------------------|----------------------------------------------------------|
| Set Budget         | `set n/BUDGET_NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY`     |
| Check Budget       | `check i/INDEX`                                          |
| List Budgets       | `list`                                                   |
| Deduct from Budget | `deduct i/INDEX a/AMOUNT`                                |
| Modify Budget      | `modify i/INDEX n/NAME a/AMOUNT e/YYYY-MM-DD c/CATEGORY` |


### Saving

| Feature              | Command Format                            |
|----------------------|-------------------------------------------|
| Set Saving Goal      | `set n/GOAL_NAME a/AMOUNT b/YYYY-MM-DD`   |
| Contribute to Saving | `contribute i/INDEX a/AMOUNT`             |
| List Saving Goals    | `list`                                    |
| Delete Saving        | `delete-s i/INDEX`                        |
| Delete Contribution  | `delete-s i/INDEX_S c/INDEX_C`            |
| Check Saving         | `check i/INDEX`                           |


### Loans

| Feature                 | Command Format       |
|-------------------------|----------------------|
| Add Loan                | `add`                |
| Edit Loan attribute     | `edit X [attribute]` |
| List Loans              | `list`               |
| Delete Loan             | `delete X`           |
| Show Loan Details       | `show X`             |
| Return loan             | `return X`           |
| Unreturn loan           | `unreturn X`         |
| Find associated loans   | `find [name]`        |
| Find outgoing loans     | `find outgoing loan` |
| Find incoming loans     | `find incoming loan` |
| Find overdue loans      | `find overdue loan`  |
| Find urgent loans       | `find X urgent loan` |
| Find largest loans      | `find largest loan`  |
| Help                    | `help`               |
| Help with find commands | `help find`          |
| Help with edit commands | `help edit`          |



### Analytics & Program Setup

| Feature                           | Command Format                                         |
|-----------------------------------|--------------------------------------------------------|
| Display monthly financial summary | `overview [yyyy-mm]`                                   |
| Display financial trends          | `trend <data-type> <start-date> <end-date> <interval>` |
| Display spending insights         | `insight [yyyy-mm]`                                    |
| Display breakdown of spending     | `spending-breakdown [yyyy-mm]`                         |
| Set Currency                      | `setup`                                                |




## Possible Enhancements

- Recurring entries for expense and income (e.g., monthly salary)
- Exporting to CSV
- Search by keyword or date range
- Gives a saving summary upon completing a saving goal
- Giving the user the freedom to modify their saving
- Giving user the freedom to delete any budget they want
- Give user the freedom to filter the Budgets/Savings to list out based on their active status
- Multiple users and user storages
- Better integration of ContactsList
- More loan types
- New analytic features focused on Savings and Loan such as Budget Warning, Loan Due Warning, etc
