package bookstoreapp;

import java.io.*;
import java.util.ArrayList;

public class DataManager {

    public static void loadBooks() {
        BookList bookList = BookList.getInstance();
        bookList.getBooks().clear();
        File file = new File("books.txt");
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    bookList.addBook(new Book(name, price));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    public static void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("books.txt"))) {
            ArrayList<Book> books = BookList.getInstance().getBooks();
            for (Book b : books) {
                pw.println(b.getName() + "," + b.getPrice());
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public static void loadCustomers() {
        CustomerList customerList = CustomerList.getInstance();
        customerList.getCustomers().clear();
        File file = new File("customers.txt");
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    customerList.addCustomer(new Customer(username, password, points));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }

    public static void saveCustomers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("customers.txt"))) {
            ArrayList<Customer> customers = CustomerList.getInstance().getCustomers();
            for (Customer c : customers) {
                pw.println(c.getUsername() + "," + c.getPassword() + "," + c.getPoints());
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }
}
