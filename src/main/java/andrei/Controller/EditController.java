package andrei.Controller;

import andrei.logic.AbstractBLL;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This class provides the Controller for the creation/update window.
 * The text fields are dynamically created through reflection and placed at predefined positions
 * on the window. Whenever an error is returned by any logic/database method calls, an alert will
 * be shown that describes the error that was encountered.
 * @param <T> -> type to be edited
 */
public class EditController<T> {
    @FXML private AnchorPane anchor;
    @FXML private Label labelId;
    private ArrayList<TextField> textFields;
    private ArrayList<Label> labels;
    private Class<T> type;
    private T obj;
    private TableController<T> controller;

    /**
     * If the user accepts the edit, creation, the field information will be extracted with reflection,
     * an object will be created with the required fields and then passed onto the database for further processing.
     * If a string was returned, showAlert() will be called and an alert will be displayed on the screen.
     * @param e -> parameter used to close the stage
     */
    @FXML
    public void onAccept(ActionEvent e) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IntrospectionException {
        if(labelId.getText().equals(""))
        {
            AbstractBLL<T> abstractBLL = controller.getBLLReference(type);
            int i = 0;
            T entry = type.getDeclaredConstructor().newInstance();
            Field[] fields = type.getDeclaredFields();
            if(!extractFieldInformation(i, entry, fields)) return;
            String err = abstractBLL.insert(entry);
            if(err!=null)
            {
                showAlert(err);
                return;
            }
        }
        else
        {
            AbstractBLL<T> abstractBLL = controller.getBLLReference(type);
            Field[] fields = type.getDeclaredFields();
            int i = 0;
            if(!extractFieldInformation(i, obj, fields)) return;
            String err = abstractBLL.update(obj);
            if(err!=null)
            {
                showAlert(err);
                return;
            }
        }
        controller.refreshTable();
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * This method will extract the information from the text fields. Will return false if the data cannot be parsed properly.
     * Further validation will be provided by the controller.
     */
    private boolean extractFieldInformation(int i, T entry, Field[] fields) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        for (Field field : fields) {
            if(i==0) {
                i++;
                continue;
            }
            PropertyDescriptor propertyDescriptor =
                    new PropertyDescriptor(field.getName(), type);
            Method m = propertyDescriptor.getWriteMethod();
            if(field.getType() == int.class)
            {
                int parsedInt = 0;
                try {
                    parsedInt = Integer.parseInt(textFields.get(i-1).getText());
                }catch (NumberFormatException e)
                {
                    showAlert("Integer could not be parsed at " + field.getName());
                    return false;
                }
                if(parsedInt < 0)
                {
                    showAlert("Integer values cannot be negative at " + field.getName());
                    return false;
                }
                m.invoke(entry, parsedInt );
            }
            else m.invoke(entry, textFields.get(i-1).getText());
            i++;
        }
        return true;
    }

    /**
     * @param e -> parameter used to close the stage
     */
    public void onCancel(ActionEvent e) {
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * The object received as parameter will be used to populate the text fields (which are stored in
     * the TextField array) with the information already present in the database.
     * Thus, it will be clear to the user what properties are actually changing and which are left as-is.
     * The label is also set when i==0, however this is not an editable field for the user.
     * @param toEdit -> object with the required information
     */
    @FXML
    public void loadData(T toEdit) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        int i=0;
        for (Field field : fields)
        {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
            Method method = propertyDescriptor.getReadMethod();
            if(i==0) {
                labelId.setText(type.getSimpleName().toLowerCase() +" id = "+ method.invoke(toEdit));
                i++;
                continue;
            }
            textFields.get(i-1).setText(String.valueOf(method.invoke(toEdit)) );
            i++;
        }
        obj = toEdit;
    }

    /**
     * Initially, the window has only the cancel and accept buttons.
     * I generated the rest of the fields using reflection, set them at a certain x and y
     * from the chosen starting location. The UI elements are held in a variable size
     * list of label and text fields.
     * @param type -> type parameter for controller: Order, Product or Client
     */
    public void setType(Class<T> type)
    {
        this.type = type;
        textFields = new ArrayList<>();
        labels = new ArrayList<>();
        int startXLabel = 195;
        int startYLabel = 138;
        int startXTextField = 306;
        int startYTextField = 136;
        Field[] fields = type.getDeclaredFields();
        int i=0;
        for (Field field : fields) {
           if(i==0) {i++;continue;}
           String name = field.getName();
           TextField textField = new TextField();
           Label label = new Label();
           textField.setLayoutX(startXTextField);
           textField.setLayoutY(startYTextField + (i*40));
           label.setLayoutX(startXLabel);
           label.setLayoutY(startYLabel +(i*40));
           label.setText(name);
           label.setFont(Font.font("Segoe UI",FontWeight.BOLD,15));
           label.setVisible(true);
           textField.setVisible(true);
           textFields.add(textField);
           labels.add(label);
           i++;
        }
        anchor.getChildren().addAll(textFields);
        anchor.getChildren().addAll(labels);
        labelId.setText("");
    }
    public void setController(TableController<T> controller) {
        this.controller = controller;
    }

    /**
     * Generates and shows an alert with the provided text given as parameter to method.
     * @param msg -> message to be set
     */
    private void showAlert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR,msg);
        alert.show();
    }
}
