package andrei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * The main class. Will start the application.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 521);
        stage.setTitle("Main Window");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:" + Paths.get("warehouse.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}