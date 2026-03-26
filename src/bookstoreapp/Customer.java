package bookstoreapp;

import java.util.ArrayList;

public class Customer extends User {
    private int points;
    private State state;
    private ArrayList<Book> selectedBooks;

    public Customer(String username, String password, int points) {
        super(username, password);
        this.points = points;
        this.selectedBooks = new ArrayList<>();
        if (points >= 1000) {
            this.state = new Gold();
        } else {
            this.state = new Silver();
        }
    }

    public int getPoints() {
        return points;
    }

    public String getStatus() {
        return state.getStatusName();
    }

    public void addSelectedBook(Book b) {
        selectedBooks.add(b);
    }

    public void clearSelectedBooks() {
        selectedBooks.clear();
    }

    public double buyBooks() {
        double totalCost = 0;
        for (Book b : selectedBooks) {
            totalCost += b.getPrice();
        }
        // Earn 10 points per 1 CAD spent
        int earnedPoints = (int) (totalCost * 10);
        points += earnedPoints;
        updateStatus();
        clearSelectedBooks();
        return totalCost;
    }

    public double redeemPointsAndBuy() {
        double totalCost = 0;
        for (Book b : selectedBooks) {
            totalCost += b.getPrice();
        }
        // Redeem points: 100 points = 1 CAD deduction
        // Use all accumulated points when possible
        double discount = points / 100.0;
        if (discount >= totalCost) {
            // Points cover the entire cost
            int pointsUsed = (int) (totalCost * 100);
            points -= pointsUsed;
            totalCost = 0;
        } else {
            // Use all points
            totalCost -= discount;
            points = 0;
        }
        // Earn 10 points per 1 CAD actually spent
        int earnedPoints = (int) (totalCost * 10);
        points += earnedPoints;
        updateStatus();
        clearSelectedBooks();
        return totalCost;
    }

    public void updateStatus() {
        state.updateStatus(this);
    }

    public void setState(State s) {
        this.state = s;
    }
}
