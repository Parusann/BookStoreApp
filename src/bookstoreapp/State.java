package bookstoreapp;

public interface State {
    String getStatusName();
    void updateStatus(Customer c);
}
