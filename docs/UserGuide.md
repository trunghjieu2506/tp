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

## FAQ

**Q:** What happens if I forget to type the date?  
**A:** The system automatically uses today’s date.

**Q:** Can I use lowercase for category names?  
**A:** Yes. The system normalizes category casing.

## Command Summary

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

## Coming Soon

- Recurring entries (e.g., monthly salary)
- Exporting to CSV
- Search by keyword or date range
