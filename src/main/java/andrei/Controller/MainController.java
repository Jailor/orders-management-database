package andrei.Controller;

import andrei.Main;
import andrei.model.Client;
import andrei.model.Orders;
import andrei.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;


/**
 * The controller for the main window. Very simple, has only 3 buttons.
 * Clicking any of the buttons leads to the scene changing to the corresponding one.
 * The scenes have a button that when pressed will cause a return to this main window.
 * No other notable methods.
 */
public class MainController {
    @SuppressWarnings("unchecked")
    @FXML
    protected void onClientTableClick(ActionEvent event) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        URL fxmlLocation = Main.class.getResource("table-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setTitle("Client Table");
        stage.setScene(scene);
        ((TableController<Client>)fxmlLoader.getController()).setType(Client.class);
        ((TableController<Client>)fxmlLoader.getController()).addTableColumns();
    }
    @SuppressWarnings("unchecked")
    @FXML
    protected void onProductTableClick(ActionEvent event) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        URL fxmlLocation = Main.class.getResource("table-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setTitle("Product Table");
        stage.setScene(scene);
        ((TableController<Product>)fxmlLoader.getController()).setType(Product.class);
        ((TableController<Product>)fxmlLoader.getController()).addTableColumns();
    }
    @SuppressWarnings("unchecked")
    @FXML
    protected void onOrdersTableClick(ActionEvent event) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        URL fxmlLocation = Main.class.getResource("table-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setTitle("Orders Table");
        stage.setScene(scene);
        ((TableController<Orders>)fxmlLoader.getController()).setType(Orders.class);
        ((TableController<Orders>)fxmlLoader.getController()).addTableColumns();
    }
}