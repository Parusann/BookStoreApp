package bookstoreapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BookStoreApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Owner owner;
    private Customer currentCustomer;

    // Login screen components
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Owner books screen components
    private JTable ownerBooksTable;
    private DefaultTableModel ownerBooksTableModel;
    private JTextField bookNameField;
    private JTextField bookPriceField;

    // Owner customers screen components
    private JTable ownerCustomersTable;
    private DefaultTableModel ownerCustomersTableModel;
    private JTextField customerUsernameField;
    private JTextField customerPasswordField;

    // Customer start screen components
    private JLabel welcomeLabel;
    private JTable customerBooksTable;
    private DefaultTableModel customerBooksTableModel;

    // Customer cost screen components
    private JLabel totalCostLabel;
    private JLabel pointsStatusLabel;

    public BookStoreApp() {
        owner = new Owner("admin", "admin");

        // Load data from files
        DataManager.loadBooks();
        DataManager.loadCustomers();

        setTitle("Bookstore App");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Save data on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataManager.saveBooks();
                DataManager.saveCustomers();
                System.exit(0);
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginScreen(), "login");
        mainPanel.add(createOwnerStartScreen(), "ownerStart");
        mainPanel.add(createOwnerBooksScreen(), "ownerBooks");
        mainPanel.add(createOwnerCustomersScreen(), "ownerCustomers");
        mainPanel.add(createCustomerStartScreen(), "customerStart");
        mainPanel.add(createCustomerCostScreen(), "customerCost");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    // ===================== LOGIN SCREEN =====================
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Welcome to the BookStore App");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(usernameField, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> handleLogin());

        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        if (username.equals("admin") && password.equals("admin")) {
            // Owner login
            usernameField.setText("");
            passwordField.setText("");
            cardLayout.show(mainPanel, "ownerStart");
        } else {
            // Customer login
            Customer c = CustomerList.getInstance().findCustomer(username);
            if (c != null && c.getPassword().equals(password)) {
                currentCustomer = c;
                usernameField.setText("");
                passwordField.setText("");
                showCustomerStartScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        }
    }

    // ===================== OWNER START SCREEN =====================
    private JPanel createOwnerStartScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton booksButton = new JButton("Books");
        booksButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(booksButton, gbc);

        JButton customersButton = new JButton("Customers");
        customersButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(customersButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        booksButton.addActionListener(e -> showOwnerBooksScreen());
        customersButton.addActionListener(e -> showOwnerCustomersScreen());
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    // ===================== OWNER BOOKS SCREEN =====================
    private JPanel createOwnerBooksScreen() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top part: table
        ownerBooksTableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ownerBooksTable = new JTable(ownerBooksTableModel);
        JScrollPane scrollPane = new JScrollPane(ownerBooksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Middle part: add fields
        JPanel middlePanel = new JPanel(new FlowLayout());
        middlePanel.add(new JLabel("Name:"));
        bookNameField = new JTextField(10);
        middlePanel.add(bookNameField);
        middlePanel.add(new JLabel("Price:"));
        bookPriceField = new JTextField(10);
        middlePanel.add(bookPriceField);
        JButton addBookButton = new JButton("Add");
        middlePanel.add(addBookButton);
        panel.add(middlePanel, BorderLayout.NORTH);

        // Swap: table in center, fields at top (matching the spec: top=table, middle=fields, bottom=buttons)
        // Let me restructure properly
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel midPanel = new JPanel(new FlowLayout());
        midPanel.add(new JLabel("Name:"));
        midPanel.add(bookNameField);
        midPanel.add(new JLabel("Price:"));
        midPanel.add(bookPriceField);
        midPanel.add(addBookButton);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton deleteBookButton = new JButton("Delete");
        JButton backFromBooksButton = new JButton("Back");
        bottomPanel.add(deleteBookButton);
        bottomPanel.add(backFromBooksButton);

        panel.removeAll();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(midPanel, BorderLayout.SOUTH);

        // Combine mid and bottom into a south panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(midPanel, BorderLayout.NORTH);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        panel.removeAll();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        addBookButton.addActionListener(e -> {
            String name = bookNameField.getText().trim();
            String priceStr = bookPriceField.getText().trim();
            if (name.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and price.");
                return;
            }
            try {
                double price = Double.parseDouble(priceStr);
                if (price < 0) {
                    JOptionPane.showMessageDialog(this, "Price must be non-negative.");
                    return;
                }
                // Check for duplicate book
                if (BookList.getInstance().findBook(name) != null) {
                    JOptionPane.showMessageDialog(this, "Book already exists.");
                    return;
                }
                Book b = new Book(name, price);
                owner.addBook(b);
                ownerBooksTableModel.addRow(new Object[]{name, price});
                bookNameField.setText("");
                bookPriceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price.");
            }
        });

        deleteBookButton.addActionListener(e -> {
            int selectedRow = ownerBooksTable.getSelectedRow();
            if (selectedRow >= 0) {
                String bookName = (String) ownerBooksTableModel.getValueAt(selectedRow, 0);
                owner.deleteBook(bookName);
                ownerBooksTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            }
        });

        backFromBooksButton.addActionListener(e -> cardLayout.show(mainPanel, "ownerStart"));

        return panel;
    }

    private void showOwnerBooksScreen() {
        // Refresh table
        ownerBooksTableModel.setRowCount(0);
        for (Book b : BookList.getInstance().getBooks()) {
            ownerBooksTableModel.addRow(new Object[]{b.getName(), b.getPrice()});
        }
        cardLayout.show(mainPanel, "ownerBooks");
    }

    // ===================== OWNER CUSTOMERS SCREEN =====================
    private JPanel createOwnerCustomersScreen() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top part: table
        ownerCustomersTableModel = new DefaultTableModel(new String[]{"Username", "Password", "Points"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ownerCustomersTable = new JTable(ownerCustomersTableModel);
        JScrollPane scrollPane = new JScrollPane(ownerCustomersTable);

        // Middle part: add fields
        JPanel midPanel = new JPanel(new FlowLayout());
        midPanel.add(new JLabel("Username:"));
        customerUsernameField = new JTextField(10);
        midPanel.add(customerUsernameField);
        midPanel.add(new JLabel("Password:"));
        customerPasswordField = new JTextField(10);
        midPanel.add(customerPasswordField);
        JButton addCustomerButton = new JButton("Add");
        midPanel.add(addCustomerButton);

        // Bottom part: delete and back buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton deleteCustomerButton = new JButton("Delete");
        JButton backFromCustomersButton = new JButton("Back");
        bottomPanel.add(deleteCustomerButton);
        bottomPanel.add(backFromCustomersButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(midPanel, BorderLayout.NORTH);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        addCustomerButton.addActionListener(e -> {
            String uname = customerUsernameField.getText().trim();
            String pwd = customerPasswordField.getText().trim();
            if (uname.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }
            // Check for duplicate or reserved username
            if (uname.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Cannot use 'admin' as customer username.");
                return;
            }
            if (CustomerList.getInstance().findCustomer(uname) != null) {
                JOptionPane.showMessageDialog(this, "Customer already exists.");
                return;
            }
            Customer c = new Customer(uname, pwd, 0);
            owner.addCustomer(c);
            ownerCustomersTableModel.addRow(new Object[]{uname, pwd, 0});
            customerUsernameField.setText("");
            customerPasswordField.setText("");
        });

        deleteCustomerButton.addActionListener(e -> {
            int selectedRow = ownerCustomersTable.getSelectedRow();
            if (selectedRow >= 0) {
                String custName = (String) ownerCustomersTableModel.getValueAt(selectedRow, 0);
                owner.deleteCustomer(custName);
                ownerCustomersTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
            }
        });

        backFromCustomersButton.addActionListener(e -> cardLayout.show(mainPanel, "ownerStart"));

        return panel;
    }

    private void showOwnerCustomersScreen() {
        // Refresh table
        ownerCustomersTableModel.setRowCount(0);
        for (Customer c : CustomerList.getInstance().getCustomers()) {
            ownerCustomersTableModel.addRow(new Object[]{c.getUsername(), c.getPassword(), c.getPoints()});
        }
        cardLayout.show(mainPanel, "ownerCustomers");
    }

    // ===================== CUSTOMER START SCREEN =====================
    private JPanel createCustomerStartScreen() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top part: welcome message
        welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Middle part: books table with checkboxes
        customerBooksTableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only checkbox column is editable
            }
        };
        customerBooksTable = new JTable(customerBooksTableModel);
        JScrollPane scrollPane = new JScrollPane(customerBooksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom part: buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton buyButton = new JButton("Buy");
        JButton redeemButton = new JButton("Redeem points and Buy");
        JButton logoutButton = new JButton("Logout");
        bottomPanel.add(buyButton);
        bottomPanel.add(redeemButton);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        buyButton.addActionListener(e -> handleBuy(false));
        redeemButton.addActionListener(e -> handleBuy(true));
        logoutButton.addActionListener(e -> {
            currentCustomer = null;
            cardLayout.show(mainPanel, "login");
        });

        return panel;
    }

    private void showCustomerStartScreen() {
        welcomeLabel.setText("Welcome " + currentCustomer.getUsername() +
                ". You have " + currentCustomer.getPoints() +
                " points. Your status is " + currentCustomer.getStatus());

        // Refresh books table
        customerBooksTableModel.setRowCount(0);
        for (Book b : BookList.getInstance().getBooks()) {
            customerBooksTableModel.addRow(new Object[]{b.getName(), b.getPrice(), false});
        }
        cardLayout.show(mainPanel, "customerStart");
    }

    private void handleBuy(boolean redeemPoints) {
        currentCustomer.clearSelectedBooks();

        // Collect selected books
        boolean anySelected = false;
        for (int i = 0; i < customerBooksTableModel.getRowCount(); i++) {
            Boolean selected = (Boolean) customerBooksTableModel.getValueAt(i, 2);
            if (selected != null && selected) {
                String bookName = (String) customerBooksTableModel.getValueAt(i, 0);
                Book b = BookList.getInstance().findBook(bookName);
                if (b != null) {
                    currentCustomer.addSelectedBook(b);
                    anySelected = true;
                }
            }
        }

        if (!anySelected) {
            JOptionPane.showMessageDialog(this, "Please select at least one book to buy.");
            return;
        }

        double totalCost;
        if (redeemPoints) {
            totalCost = currentCustomer.redeemPointsAndBuy();
        } else {
            totalCost = currentCustomer.buyBooks();
        }

        showCustomerCostScreen(totalCost);
    }

    // ===================== CUSTOMER COST SCREEN =====================
    private JPanel createCustomerCostScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        totalCostLabel = new JLabel("Total Cost: 0");
        totalCostLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(totalCostLabel, gbc);

        pointsStatusLabel = new JLabel("Points: 0, Status: Silver");
        pointsStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(pointsStatusLabel, gbc);

        JButton logoutButton = new JButton("Logout");
        gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        logoutButton.addActionListener(e -> {
            currentCustomer = null;
            cardLayout.show(mainPanel, "login");
        });

        return panel;
    }

    private void showCustomerCostScreen(double totalCost) {
        totalCostLabel.setText("Total Cost: " + String.format("%.1f", totalCost));
        pointsStatusLabel.setText("Points: " + currentCustomer.getPoints() +
                ", Status: " + currentCustomer.getStatus());
        cardLayout.show(mainPanel, "customerCost");
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookStoreApp app = new BookStoreApp();
            app.setVisible(true);
        });
    }
}
