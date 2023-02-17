package andrei.logic.validators;

import andrei.model.Client;

public class ClientValidator implements Validator<Client> {
    @Override
    public String validate(Client client) {
        if(client.getName().equals("")) return "Name cannot be empty";
        if(client.getAddress().equals("")) return "Address field cannot be empty";
        if(!client.getPhone_number().matches("\\d{10,}")) return "Phone number invalid";
        return null;
    }
}
