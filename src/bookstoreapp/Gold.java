package bookstoreapp;

public class Gold implements State {

    @Override
    public String getStatusName() {
        return "Gold";
    }

    @Override
    public void updateStatus(Customer c) {
        if (c.getPoints() < 1000) {
            c.setState(new Silver());
        }
    }
}
