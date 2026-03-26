package bookstoreapp;

public class Owner extends User {

    public Owner(String username, String password) {
        super(username, password);
    }

    public void addBook(Book b) {
        BookList.getInstance().addBook(b);
    }

    public void deleteBook(String name) {
        BookList.getInstance().deleteBook(name);
    }

    public void addCustomer(Customer c) {
        CustomerList.getInstance().addCustomer(c);
    }

    public void deleteCustomer(String username) {
        CustomerList.getInstance().deleteCustomer(username);
    }
}
