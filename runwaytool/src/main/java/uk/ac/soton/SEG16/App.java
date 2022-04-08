package uk.ac.soton.SEG16;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;


/**
 * JavaFX App
 */
public class App extends Application {

    public Stage stage;
    public static Scene scene;

    public static String MainPath;
    public static String RunwaysPath;
    public static String ObstaclesPath;

    public static App INSTANCE;

    @Override
    public void start(Stage stage) throws Exception {
        // Singleton Pattern
        INSTANCE = this;
        // Singleton Pattern

        MainPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/runwaytool/";
        RunwaysPath = MainPath + "runways/";
        ObstaclesPath = MainPath + "obstacles/";
        try {
            Files.createDirectories(Paths.get(MainPath));
            Files.createDirectories(Paths.get(RunwaysPath));
            Files.createDirectories(Paths.get(ObstaclesPath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/root.fxml"));
        Parent root = loader.load();

        stage.setTitle("Runway Tool");
        stage.setMaximized(true);
        stage.setWidth(1320);
        stage.setHeight(800);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        this.scene = scene;
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}