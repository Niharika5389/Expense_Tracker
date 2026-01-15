# Expense Tracker (Java)

A menu-driven Expense Tracker built using Java and OOP concepts.  
It supports adding, viewing, deleting expenses, calculating totals, and saving data using CSV file storage.

## Features
- Add expense (amount + category)
- View all expenses
- Total expense calculation
- Category-wise total calculation
- Delete expense by index
- Input validation using custom exceptions
- File persistence using `expenses.csv`

## Concepts Used
- OOP (Classes, Encapsulation, Getters)
- ArrayList
- Custom Exceptions
- File Handling (BufferedReader / BufferedWriter)
- Scanner input validation (`hasNextInt`, `hasNextDouble`)

## How to Run
1. Compile:
   ```bash
   javac TrackerApp.java
   java TrackerApp

## File Storage

This project stores expenses in a CSV file named `expenses.csv`, where each expense is saved on a new line in the format `amount,category` (for example: `240,Home`). The application automatically loads existing expenses from this file when it starts, and updates the file whenever an expense is added or deleted to ensure data persists between runs.

