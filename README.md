# Book Store Application

A desktop Java application for managing a small bookstore with two user roles:

- `Owner` can manage books and customers.
- `Customer` can browse books, purchase selected books, redeem loyalty points, and track membership status.

The project uses a Java Swing interface, stores data in plain text files, and follows a simple object-oriented design with `Singleton` and `State` patterns.

## Features

- Login system for owner and customers
- Owner dashboard for:
  - adding books
  - deleting books
  - adding customers
  - deleting customers
- Customer dashboard for:
  - viewing available books
  - selecting multiple books to purchase
  - buying with regular payment
  - redeeming points toward a purchase
- Loyalty program with customer status levels:
  - `Silver`
  - `Gold`
- Automatic data persistence using:
  - `books.txt`
  - `customers.txt`

## Default Owner Credentials

Use the following credentials to log in as the owner:

- Username: `admin`
- Password: `admin`

## Loyalty Points Rules

- Customers earn `10 points` for every `1 CAD` spent.
- Customers can redeem points at a rate of `100 points = 1 CAD`.
- A customer becomes `Gold` when they reach `1000` or more points.
- A customer returns to `Silver` if their points drop below `1000`.

## Tech Stack

- Java
- Java Swing
- NetBeans project structure
- Plain text file storage

## Project Structure

```text
BookStoreApp/
|-- src/bookstoreapp/
|   |-- Book.java
|   |-- BookList.java
|   |-- BookStoreApp.java
|   |-- Customer.java
|   |-- CustomerList.java
|   |-- DataManager.java
|   |-- Gold.java
|   |-- Owner.java
|   |-- Silver.java
|   |-- State.java
|   `-- User.java
|-- books.txt
|-- customers.txt
|-- build.xml
|-- manifest.mf
`-- nbproject/
```

## Design Overview

### Core Classes

- `Book` stores book name and price.
- `Customer` stores account information, loyalty points, selected books, and membership status.
- `Owner` manages bookstore inventory and customer accounts.
- `User` is the base class for owner and customer accounts.

### Patterns Used

- `Singleton`
  - `BookList` maintains one shared list of books.
  - `CustomerList` maintains one shared list of customers.
- `State`
  - `Silver` and `Gold` represent customer loyalty levels.
  - `State` defines status behavior and transitions.

### Data Persistence

`DataManager` loads data from `books.txt` and `customers.txt` when the application starts, then saves updates back to those files when the window closes.

## Data File Format

### `books.txt`

Each line:

```text
book_name,price
```

Example:

```text
The Hobbit,24.99
Clean Code,39.50
```

### `customers.txt`

Each line:

```text
username,password,points
```

Example:

```text
alice,alice123,250
bob,bob123,1200
```

## How to Run

### Option 1: Run in NetBeans

1. Open the project in NetBeans.
2. Make sure the project root contains `books.txt` and `customers.txt`.
3. Run the main class:

```text
bookstoreapp.BookStoreApp
```

### Option 2: Run from the Command Line

From the project root:

```bash
javac -d out src/bookstoreapp/*.java
java -cp out bookstoreapp.BookStoreApp
```

Important:

- Run the app from the project root so it can read and write `books.txt` and `customers.txt`.
- This command-line compile path was verified in a local check.

## Notes

- The repository includes NetBeans/Ant project files (`build.xml` and `nbproject/`).
- `books.txt` and `customers.txt` can start empty.
- Duplicate books and duplicate customer usernames are prevented in the UI.
- The username `admin` is reserved for the owner account.

## Possible Improvements

- Encrypt stored passwords
- Add input validation for special characters and malformed records
- Add search, sorting, and filtering for books
- Improve exception handling and logging
- Add unit tests for business logic
- Package the app as an executable JAR for easier distribution

## Author

Parusann
