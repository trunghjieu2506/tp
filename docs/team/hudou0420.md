# CashFlow's Project Portfolio Page

## Project: CashFlow v2.0

**CashFlow** is a desktop financial tracking application used by university students who are fluent with CLI. 
It comes with a few features including Budget, Savings, Income, Expenses, Loan and Analytics.
User is able to easily navigate through different feature manager, and input commands with minimum command lines
required.

The application also integrated for Budget and Expense Management system for an automated expense recording in Budget,
when the user adds a new expense with conditions (see Developer Guide).

---

## Summary of Contributions

### 1. Budget Management Features
- **Set Budget**: Set a new budget with a deadline, name, amount and category
- **Add to Budget**
- **Deduct from Budget**
- **List Budget**: List out all budgets and their remaining money
- **Budget Exceed Alerts**: Alert user when budget exceeds, but do not restrict user from spending
- **Check Detail Expenses of a Budget**: List out all expense histories in the budget
- **Utils, Commands and Parsers**: Helper class which helps to execute the programs


- **What it does**: Manage multiple budgets with basic commands
- **Highlights**: Added more colourful commands in CLI, for clearer text identification
Integrates with expense for auto budget deduction upon adding an expense


#### 2. Saving Management
- **Set Saving**: Set a new saving with a deadline, name and amount
- **Add Contributions**: Add a contribution to a saving by depositing money into the saving
- **List Savings**: List out all savings and their remaining money to contribute
- **Check Savings**: Check on the details of a saving, including all contributions
- **Saving Completion Alerts**: Alert user when budget completes
- **Delete Saving**: The user is able to delete a saving
- **Delete Contribution**: The user is able to delete a contribution within a saving. This will remove the amount 
they have contributed to the saving
- **Utils, Commands and Parsers**: Helper class which helps to execute the programs
- **Contributions**: A subclass of Saving to record each contribution to a saving, with amount and date
- **What it does**: Manage multiple savings with basic commands
- **Highlights**: Added more colourful commands in CLI, for clearer text identification
  Integrates with expense for auto budget deduction upon adding an expense

#### 3. Utilities in the overall projects
- **Colour Scheme**: Created colour class, initially for budget, but expands to all, to add colours to text
- **IO Handler**: Created a skeleton structure of the IO Handler to handle all IO from user and prints to user



---

### Code Contributed
- View my contributions via [RepoSense](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=Hudou0420&tabRepo=AY2425S2-CS2113-W11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false).

---

### Project Management
- Managed GitHub releases: `v1.0`, `v2.0`, and `v2.1`
- Created and maintained GitHub issues, milestones
- Addressed Github issues posted by the PE-D, and also planning to address issues on PE

---

### Enhancements to Existing Features
- Updated CLI text color palette
- Updated to a more user friendly prompt, so that user can navigate more easily in the budget and saving component
- Added tests to increase coverage from **39%** to **60%**

---

### Documentation

#### User Guide
- Listed all features, and command details for Budget and Saving Management

#### Developer Guide
- Added design architecture for the Budget and Saving Management system
- Added implementation details for Budget and Saving Management
- Added UML diagrams for illustrating the Budget and Saving classes
- Added test implementations for Budget and Saving methods
- Added User stories for the Budget and Saving part

---

### Contributions to Team Based Tasks
- Reviewed and approved PRs with meaningful feedbacks
- Collaborate with team mates to integrate between features
- Managed the milestone and issue creations
- Resolve Merge Conflicts from any PRs
- Managed team deadlines and remind team about the deliverables
- Tested other team members code for robustness of the application

---
### Contributions Beyond the Project Team
- Give peer reviews to other teams regarding their DG
- Explains file structures to other team on minimising conflicts


---
