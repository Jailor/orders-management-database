package andrei.Controller;


import andrei.Main;
import andrei.logic.AbstractBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.List;
import javafx.event.ActionEvent;


/**
 * This is the generic controller for the table. The table will be shown on this scene,
 * alongside with the buttons for create/update/delete.
 * @param <T> -> type for table
 */
public class TableController<T> {
    private Class<T> type;
    @FXML
    private TableView<T> tableT;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;

    public static final String LOGIC_LOCATION = "andrei.logic.";

    /**
     * Returns to the main window.
     */
    @FXML
    public void onMainWindow(ActionEvent event) throws IOException {
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        URL fxmlLocation = Main.class.getResource("main-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setTitle("Main window");
        stage.setScene(scene);
    }

    /**
     * The edit view will be loaded with the option of editing an object: text fields and id will be filled with
     * the corresponding data.
     */
    @SuppressWarnings("unchecked")
    @FXML
    public void onEdit() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IntrospectionException {
        T entry = tableT.getSelectionModel().getSelectedItem();
        if(entry==null)
        {
            showAlert();
        }
        else
        {
            Stage stage = new Stage();
            URL fxmlLocation = Main.class.getResource("entry-edit.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 720, 521);
            stage.setScene(scene);
            ((EditController<T>)fxmlLoader.getController()).setType(type);
            ((EditController<T>)fxmlLoader.getController()).setController(this);
            ((EditController<T>) fxmlLoader.getController()).loadData(entry);
            stage.show();
        }
        //update table
        refreshTable();
    }

    /**
     * The edit view will be loaded with the option of adding an object: empty fields and empty label
     */
    @SuppressWarnings("unchecked")
    @FXML
    public void onCreate() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Stage stage = new Stage();
        URL fxmlLocation = Main.class.getResource("entry-edit.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setScene(scene);
        ((EditController<T>)fxmlLoader.getController()).setType(type);
        ((EditController<T>)fxmlLoader.getController()).setController(this);
        stage.show();
        //update table
        refreshTable();
    }

    /**
     * The method will check if the user has made a selection. If not, an alert will be shown.
     * Next, the user will be asked if they want to confirm their selection. If the user is sure
     * about the deletion, a BLL will be requested and the call to delete will be given.
     */
    @FXML
    public void onDelete () {
        T entry = tableT.getSelectionModel().getSelectedItem();
        if(entry==null)
        {
            showAlert();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Entry Confirmation Alert");
            alert.setContentText("Are you sure that you want to delete this entry?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    try {
                        AbstractBLL<T> abstractBLL = getBLLReference(type);
                        String err = abstractBLL.delete(entry);
                        if(err != null)
                        {
                            showAlert(err);
                            return;
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        refreshTable();
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * This method generates the table header using reflection. Columns are added for each field in the object
     * and the table sorting strategy is set to be sorted on id. Then, the method to populate the table with data
     * is called.
     */
    public void addTableColumns() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        for(Field field: fields)
        {
            String name = field.getName();
            TableColumn<T,Integer> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>(name));
            tableT.getColumns().add(column);
        }
        //add default sort policy
        tableT.getSortOrder().add(tableT.getColumns().get(0));
        //get elements
        refreshTable();
    }

    /**
     * Method will refresh table data with what is present in the database. A reference to business class is obtained
     * and the table is populated using the findAll statement in the DAO. A call to TableView.setAll() follows which
     * will populate the whole table using the objects generated by the findAll method.
     */
    public void refreshTable() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractBLL<T> abstractBLL = getBLLReference(type);
        List<T> lst =  abstractBLL.findAll();
        tableT.getItems().setAll(lst);
        tableT.sort();
    }

    /**
     * A generic alert telling the user that they haven't made a selection in the table before pressing edit or create.
     */
    private void showAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR,"Please select a field from the table first!");
        alert.show();
    }

    private void showAlert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR,msg);
        alert.show();
    }

    /**
     * During the initialisation, the buttons are given a name based on the functionality and the
     * target class that is being used.
     * @param targetClass -> generic class used for name
     */
    public void setType(Class<T> targetClass)
    {
        this.type = targetClass;
        createButton.setText("Create " + targetClass.getSimpleName());
        editButton.setText("Edit " + targetClass.getSimpleName());
        removeButton.setText("Remove " + targetClass.getSimpleName());
    }

    /**
     * This function will dynamically search the logic package in order to find correct business logic class
     * associated with the parameter. An abstract type will be returned, however the created object will
     * be of type specific to the generic, not the abstract, so the called function will be desired ones
     * thanks to run-time polymorphism provided by java.
     *
     * @param type -> type of the business logic class that is being searched
     * @return -> business logic class of the required type
     */
    @SuppressWarnings("unchecked")
    public AbstractBLL<T> getBLLReference(Class<T> type) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = LOGIC_LOCATION + type.getSimpleName() + "BLL";
        Class<?> my_class = Class.forName(className);
        Constructor<?> ctor = my_class.getConstructor();
        Object obj = ctor.newInstance();
        return (AbstractBLL<T>)obj;
    }
}
