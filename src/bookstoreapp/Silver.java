package bookstoreapp;

public class Silver implements State {

    @Override
    public String getStatusName() {
        return "Silver";
    }

    @Override
    public void updateStatus(Customer c) {
        if (c.getPoints() >= 1000) {
            c.setState(new Gold());
        }
    }
}
