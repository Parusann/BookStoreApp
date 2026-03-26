package bookstoreapp;

import java.util.ArrayList;

public class BookList {
    private ArrayList<Book> books;
    private static BookList instance;

    private BookList() {
        books = new ArrayList<>();
    }

    public static BookList getInstance() {
        if (instance == null) {
            instance = new BookList();
        }
        return instance;
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void deleteBook(String name) {
        books.removeIf(book -> book.getName().equals(name));
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Book findBook(String name) {
        for (Book b : books) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }
}
