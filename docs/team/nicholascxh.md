# Nicholas Chong - Project Portfolio Page

## Overview

Our product, **Cashflow**, is a CLI-based application that allows users to record, categorize, and analyze their personal expenses and income. It supports commands to add, edit, delete, sort, and list financial transactions by date and category. The app is optimized for fast and lightweight usage, ideal for users who prefer a non-GUI financial tracking tool.

I was responsible for implementing the core features that manage expense and income entries, including command parsing, logic execution, and unit testing. My work emphasized clean separation of concerns and extensibility.

---

## Summary of Contributions

### 🧑‍💻 Code Contributed
- [My code on Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=nicholascxh&breakdown=true)
- Core logic for:
    - `Expense`, `Income`
    - `ExpenseManager`, `IncomeManager`
- Parser and command execution classes:
    - `ExpenseCommandParser.java`, `IncomeCommandParser.java`
    - All `Add/Edit/Delete/List/Sort/Top/BottomCategory` command classes for both Expense and Income
- Integrated category and date support for all entries

---

### ✨ Enhancements Implemented

- Expense & income object models with category and date fields
- Expense and income managers with full CRUD support and category analysis
- Command parsing and validation for CLI commands with graceful error handling
- Top and bottom category analysis logic (aggregated spending/earning by category)
- Full command pattern implementation to support extensibility

---

### 📘 Contributions to the UG

- Wrote all sections related to:
    - Expense and income commands (`add`, `edit`, `delete`, `list`, `sort`, `top`, `bottom`, `list category`)
    - Summary table and command format explanations
    - FAQ and Quick Start section

---

### 📗 Contributions to the DG

- Wrote the implementation section for:
    - Expense and Income modules
    - ExpenseManager and IncomeManager
    - Command parsing and execution architecture
- Created and documented:
    - Class diagram for Expense and Income managers
    - Sequence diagram for `AddExpenseCommand` (with activation bars and return control)

---

### 👥 Contributions to Team-based Tasks

- Setting up the GitHub team org/repo
- Necessary general code enhancements
- Setting up tools e.g., GitHub, Gradle
- Maintaining the issue tracker
- Release management
- Participated in design meetings and issue triaging
- Reminded teammates on deliverables

---

### 🤝 Review / Mentoring Contributions

- [PRs Reviewed By Me](https://github.com/AY2425S2-CS2113-W11-2/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3A%40me)
- Reviewed PRs related to UI cleanup, input parsing, and error handling

---

### 🌐 Contributions Beyond the Team

- Raised GitHub issues on bugs discovered in peer teams during PE dry runs

---

### Class Diagram - Expense and Income Manager

![ExpenseManager and IncomeManager](../img.png)

### Sequence Diagram - Add Expense Command

![AddExpenseCommand Sequence](../img_3.png)