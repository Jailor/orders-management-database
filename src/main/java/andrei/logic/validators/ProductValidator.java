package andrei.logic.validators;

import andrei.model.Product;

public class ProductValidator implements Validator<Product> {
    @Override
    public String validate(Product product) {
        if(product.getName().equals("")) return "Name cannot be empty";
        if(product.getPrice()==0) return "Product cannot be free";
        return null;
    }
}
