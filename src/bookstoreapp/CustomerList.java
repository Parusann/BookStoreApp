package bookstoreapp;

import java.util.ArrayList;

public class CustomerList {
    private ArrayList<Customer> customers;
    private static CustomerList instance;

    private CustomerList() {
        customers = new ArrayList<>();
    }

    public static CustomerList getInstance() {
        if (instance == null) {
            instance = new CustomerList();
        }
        return instance;
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void deleteCustomer(String username) {
        customers.removeIf(cust -> cust.getUsername().equals(username));
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public Customer findCustomer(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }
}
