package andrei.model;

/**
 * This class models the product table from the database. Contains information about the created products.
 * Java naming conventions are not followed because PostGreSQL is not case-sensitive.
 */
public class Product {

    private int product_id;
    private String name;
    private int quantity;
    private int price;

    public Product(int product_id, String name, int quantity, int price) {
        this.product_id = product_id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public Product()
    {

    }

    //GETTERS AND SETTERS

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}