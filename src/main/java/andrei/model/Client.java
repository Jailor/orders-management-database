package andrei.model;

/**
 * This class models the client table from the database. Contains information about the clients that make orders.
 * Java naming conventions are not followed because PostGreSQL is not case-sensitive.
 */
public class Client {
    private int client_id;
    private String name;
    private String address;
    private String phone_number;

    public Client(int client_id, String name, String address, String phone_number) {
        this.client_id = client_id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
    }
    public Client()
    {

    }
    //GETTERS AND SETTERS
    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}