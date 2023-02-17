package andrei.logic;

import andrei.data.ClientDAO;
import andrei.data.OrdersDAO;
import andrei.data.ProductDAO;
import andrei.model.Client;
import andrei.model.Orders;
import andrei.model.Product;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * This class is used to implement the business logic aspect of the orders.
 * Orders can only be created, updated and deleted within certain rules.
 * This class also generates the bills for the orders.
 */
public class OrdersBLL extends AbstractBLL<Orders>{
    private OrdersDAO ordersDAO;
    public OrdersBLL()
    {
        ordersDAO = new OrdersDAO();
    }

    @Override
    public List<Orders> findAll()
    {
        return ordersDAO.findAll();
    }

    /**
     * An order can only be inserted if the specified client/product exists
     * and there is enough stock. If the checks are successful, a bill is
     * generated in a .txt format with the according order number. The error code
     * is returned as a string.
     * @param obj - order to insert
     * @return error code as string
     */
    @Override
    public String insert(Orders obj) {
        ClientDAO clientDAO = new ClientDAO();
        Client clientCandidate = clientDAO.findById(obj.getClient_id());
        if(clientCandidate == null) return "Client does not exist!";
        ProductDAO productDAO = new ProductDAO();
        Product productCandidate = productDAO.findById(obj.getProduct_id());
        if(productCandidate == null) return "Product does not exist";

        if(productCandidate.getQuantity() < obj.getQuantity())
        {
            return "Insertion was not possible because there is not enough stock!\n" +
                    "Product available: " + productCandidate.getQuantity()+"\n"+
                    "Product requested: " + obj.getQuantity();
        }
        else
        {
            //DECREMENT STOCK
            productCandidate.setQuantity(productCandidate.getQuantity() - obj.getQuantity());
            productDAO.update(productCandidate);
            //INSERT
            ordersDAO.insert(obj);
            Orders generatedOrder = null;
            try {
                 generatedOrder= ordersDAO.findMaxId();
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            assert generatedOrder != null;
            generateBill(generatedOrder,productCandidate,clientCandidate);
            return null;
        }
    }

    /**
     * An order can only be updated if the specified client/product exists
     * and there is enough stock to update. If the checks are successful, the
     * corresponding bill is updated. The error code is returned as a string,
     * together with the required and generated quantities. The corresponding
     * product stock will be updated in the case of success.
     * @param obj - order to update
     * @return error code
     */
    @Override
    public String update(Orders obj) {
        ClientDAO clientDAO = new ClientDAO();
        Client clientCandidate = clientDAO.findById(obj.getClient_id());
        if(clientCandidate == null) return "Client does not exist!";
        ProductDAO productDAO = new ProductDAO();
        Product productCandidate = productDAO.findById(obj.getProduct_id());
        if(productCandidate == null) return "Product does not exist";
        Orders existing = ordersDAO.findById(obj.getOrder_id());
        if(productCandidate.getQuantity() < (obj.getQuantity() - existing.getQuantity()))
        {
            return "Insertion was not possible because there is not enough stock!\n" +
                    "Product available: " + productCandidate.getQuantity()+"\n"+
                    "Product requested in total by update: " + (obj.getQuantity() - existing.getQuantity());
        }
        else
        {
            productCandidate.setQuantity(productCandidate.getQuantity() -
                    (obj.getQuantity() - existing.getQuantity()));
            productDAO.update(productCandidate);
            ordersDAO.update(obj);
            int id = obj.getOrder_id();
            Orders updatedOrder = ordersDAO.findById(id);
            generateBill(updatedOrder,productCandidate,clientCandidate);
            return null;
        }
    }

    /**
     * An order can only be deleted. If the checks are successful, the
     *corresponding bill is deleted as well. This operation CANNOT
     *fail, however it still returns a String in order to be consistent
     * with the other functions. The product stock will be updated with
     * the difference before the function returns.
     * @param obj - order to delete
     * @return null
     */
    @Override
    public String delete(Orders obj) {
        ProductDAO productDAO = new ProductDAO();
        Product productCandidate = productDAO.findById(obj.getProduct_id());
        productCandidate.setQuantity(productCandidate.getQuantity()+obj.getQuantity());
        productDAO.update(productCandidate);
        removeBill(obj);
        ordersDAO.delete(obj);
        return null;
    }

    /**
     * This method generates a bill for the required order.
     * It also calculates the total order cost based on the
     * unit price of each product. This sum is appended at the end
     * of the file.
     * @param order - order details to be written
     * @param product - product details to be written
     * @param client - client details to be written
     */
    private void generateBill(Orders order, Product product, Client client)
    {
        try {
            FileWriter myWriter = new FileWriter("orderBills\\order"+ order.getOrder_id()+".txt");
            myWriter.write(("-------------------------------------------------\n"));
            myWriter.write("BILL for order with id " + order.getOrder_id() +"\n");
            myWriter.write(("-------------------------------------------------\n"));
            myWriter.write("Client:"+ client.getName()+"\nid:"+client.getClient_id()+
                    "\naddress:" + client.getAddress() + "\nphone number:" + client.getPhone_number() + "\n");
            myWriter.write(("-------------------------------------------------\n"));
            myWriter.write("Ordered product is "+ product.getName() + " with id "+product.getProduct_id()+
                    " at a price of "+ product.getPrice()+" per piece.\n");
            myWriter.write(("-------------------------------------------------\n"));
            myWriter.write(order.getQuantity() +" units were ordered with a total price of " +
                            order.getQuantity()* product.getPrice() +"\n"
                    );
            myWriter.write(("-------------------------------------------------\n"));
            myWriter.close();
        } catch (IOException e) {
            System.err.println("An error has occurred while writing to a file!");
            e.printStackTrace();
        }
    }

    /**
     * This method removes the bill with the corresponding id.
     * @param order - order details, mainly id
     */
    private void removeBill(Orders order)
    {
        File myObj = new File("orderBills\\order"+ order.getOrder_id()+".txt");
        if (!myObj.delete()) {
            System.err.println("Failed to delete the file!");
        }
    }
}
