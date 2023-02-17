package andrei.model;

/**
 * This class models the orders table from the database. Contains information about the created orders.
 * Java naming conventions are not followed because PostGreSQL is not case-sensitive.
 */
public class Orders {

    private int order_id;
    private int client_id;
    private int product_id;
    private int quantity;

    public Orders(int order_id, int client_id, int product_id, int quantity) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }
    public Orders()
    {

    }

    //GETTERS AND SETTERS


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}