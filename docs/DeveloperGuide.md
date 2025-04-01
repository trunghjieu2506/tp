# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}
### 1. Nicholas

I was responsible for implementing the **Expense** and **Income** management modules. This includes the `Expense`, `Income`, `ExpenseManager`, `IncomeManager`, as well as all associated commands and parsers.

The goal of these modules is to allow users to accurately and efficiently manage their expenses and income with support for adding, editing, deleting, listing, categorizing, and sorting entries.

#### Expense and Income Design Overview

Both Expense and Income share a similar structure. They are managed via manager classes (`ExpenseManager`, `IncomeManager`) that encapsulate the core logic and handle all interactions with their respective data types. These managers are then accessed and used by command classes via the `Command` pattern.


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
