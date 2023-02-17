package andrei.logic;


import andrei.data.ProductDAO;
import andrei.logic.validators.ProductValidator;
import andrei.model.Orders;
import andrei.model.Product;

import java.util.List;

/**
 * See abstract BLL.
 */
public class ProductBLL extends AbstractBLL<Product>{
    ProductDAO productDAO;

    public ProductBLL()
    {
        productDAO = new ProductDAO();
    }

    @Override
    public List<Product> findAll()
    {
        return productDAO.findAll();
    }

    @Override
    public String insert(Product obj) {
        ProductValidator productValidator = new ProductValidator();
        String res = productValidator.validate(obj);
        if(res!= null) return res;
        productDAO.insert(obj);
        return null;
    }
    @Override
    public String update(Product obj) {
        productDAO.update(obj);
        return null;
    }
    public String delete(Product obj) {
        OrdersBLL ordersBLL = new OrdersBLL();
        List<Orders> lst = ordersBLL.findAll();
        for(Orders order: lst)
        {
            if(order.getProduct_id() == obj.getProduct_id()) return "Product has orders in the database, cannot delete!";
        }
        productDAO.delete(obj);
        return null;
    }
}
