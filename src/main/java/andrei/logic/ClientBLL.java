package andrei.logic;

import andrei.data.ClientDAO;
import andrei.logic.validators.ClientValidator;
import andrei.model.Client;
import andrei.model.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * See abstract bll.
 */
public class ClientBLL extends AbstractBLL<Client>{
    ClientDAO clientDAO;

    public ClientBLL()
    {
        clientDAO = new ClientDAO();
    }

    @Override
    public List<Client> findAll()
    {
        return clientDAO.findAll();
    }

    @Override
    public String insert(Client obj) {
        ClientValidator clientValidator = new ClientValidator();
        String res = clientValidator.validate(obj);
        if(res != null) return res;
        clientDAO.insert(obj);
        return null;
    }
    @Override
    public String update(Client obj) {
        ClientValidator clientValidator = new ClientValidator();
        String res = clientValidator.validate(obj);
        if(res != null) return res;
        clientDAO.update(obj);
        return null;
    }
    @Override
    public String delete(Client obj) {
        OrdersBLL ordersBLL = new OrdersBLL();
        List<Orders> lst = ordersBLL.findAll();
        for(Orders order: lst)
        {
            if(order.getClient_id() == obj.getClient_id()) return "Client has orders in the database, cannot delete!";
        }
        clientDAO.delete(obj);
        return null;
    }
}
