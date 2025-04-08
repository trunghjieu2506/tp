## Trung Hieu - Project Portfolio Page

**CashFlow** is an application that enable users to record, categorize, and analyze their personal finances including expense, income, saving, budget and loan efficiently.

I built **CashFlowManger** and **AnalyticsManager** modules. Aside Analytics Feature, I worked extensively on integration of the entire program with other team members. Below are the summary to my contributions:

### 🧑‍💻 Code Contributed & Enhancements 
[My Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Hieu&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=functional-code~docs~test-code~other&since=2025-02-21)

1. `CashFlowManager` (Coordinator for Application Logic)

- Implemented the core startup and runtime loop.

- **Integrated** all managers (Expense, Income, Savings, Budget, etc.) with the application’s main flow.

- Implemented **logging** and **exception handling** to maintain reliability and transparency.

2. `FinanceData ` (Central Data Hub)

- Designed FinanceData as a single source of truth for currency settings, managers (`ExpenseManager`, `IncomeManager`, etc.), and user configuration (`isFirstTime`).

- Established getters/setters that unify how other modules retrieve or update shared data.
- Introduced interfaces such as `ExpenseDataManager` and `SavingDataManager` to **unify** how data flows into the analytics module.
3.  `Storage ` (Persistent Storage Component)

- Introduced **serialization** for objects (e.g., Finance, SetupConfig), ensuring user data and settings persist across sessions.
- Introduced ` Finance`  parent class, from which ` Expense` , ` Income` , ` Saving` , ` Budget` , and ` Loan`  inherit. 
- By using an abstract class, serialization becomes much cleaner. The Storage class can save and load these subclasses as a unified ` ArrayList<Finance> ` without worrying about each specific subclass type.
4.  `AnalyticsManager ` (Analytic Module)

- Conducts four different analytic operations (Overview, Trend, Insights, Spending Breakdowns).

- Provided clear APIs in AnalyticsManager that unify Expense/Income managers’ data for analysis.
- Added **assertions** to guard against invalid or incomplete setup states, enhancing code robustness.
- Designed classes with distinct responsibilities to closely follow **SLAP principles**:
  - `AnalyticCommandParser`: parses analytic commands.
  - `AnalyticGeneralCommand`: Base class for analytic commands like `OverviewCommand`, `TrendCommand`, etc.
  - `AnalyticsManager`: core analytic logic 
  - Help Functions (`TrendHelper`, `InsightHelper`, `SpendingHelper`): further break down analytic logic (print charts, compute weekly, monthly, etc)
  - `AnalyticDataLoader`: fetches and aggregates data for `AnalyticManagers`

### 📘 Contributions to the User Guide (UG) & Developer Guide (DG)

- Added the following UG sections:
    - Analytic commands: `overview`, `trend`, `insight`, `spending-breakdown`
    - Introduction, command summary and possible enhancements

- Added the following DG sections:
    - CashFlowManager and AnalyticManager Design and Implementation
    - All appendixes' content and Acknowledgments
- Diagrams contributed:
    - Class Diagrams for `cashflow` package and `analytics` package, Sequence Diagrams for `OverviewCommand`

### 👥 Contributions to Team-based Tasks
 - Proactively **lead integration** of the program with the team. 
 - Frequently updated and worked with teammates to review PRs, debugs and contribute to new common features.