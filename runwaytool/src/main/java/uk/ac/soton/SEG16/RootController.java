package uk.ac.soton.SEG16;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import uk.ac.soton.SEG16.runway.Runways;
import uk.ac.soton.SEG16.runway.Runway;

public class RootController {

    @FXML private Menu preDefObstacles, preDefRunways;

    @FXML private BorderPane borderPane;

    @FXML
    public void initialize() {
        try {
            // Create the RunwayTool window, this itself will create the 3 sections
            FXMLLoader loader;
            Node node;

            loader = new FXMLLoader(getClass().getResource("fxmls/runwayTool.fxml"));
            node = loader.load();
            borderPane.setCenter(node);

            // Setup the Predefined Menus
            preDefObstacles.getItems().addAll(getPredefinedObjects());
            preDefRunways.getItems().addAll(getPredefinedRunways());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private ArrayList<MenuItem> getPredefinedObjects() {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        try {
            ArrayList<Obstacle> obs = Obstacles.FromXML("files.xml");
            obs.forEach((ob) -> {
                MenuItem mi = new MenuItem(ob.getName());
                mi.setOnAction((e) -> {
                    RunwayToolController.INSTANCE.setObstacle(ob);
                });

                items.add(mi);
            });
        } catch (Exception e) {
            RootController.showAlert(e.toString());
        }

        return items;
    }
    private ArrayList<MenuItem> getPredefinedRunways() {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

            // We had written this file in marshalling example
            // Runways runways = ((Runways) XMLLoader.load("PredefinedRunways.xml", Runways.class));
            ArrayList<Runway> runs = new ArrayList<>();
            try {
                runs = Runways.GetPredefinedRunways();
            } catch (Exception e1) {
                RootController.showAlert(e1.getMessage());
                e1.printStackTrace();
            }

            runs.forEach((obj) -> {
                MenuItem mi = new MenuItem(obj.PredefinedInfo());
                mi.setOnAction((e) -> {
                    RunwayToolController.INSTANCE.setMainRunway(obj);
                });

                items.add(mi);
            });

        return items;
    }



    //#region Runway XML
    public void FileMenu_ImportRunway_XML(ActionEvent e) throws IOException {
        // Setup the File Chooser Window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.setInitialDirectory(new File(App.RunwaysPath));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
        File file = fileChooser.showOpenDialog(App.INSTANCE.stage);

        if (file != null) {
            try {
                RunwayToolController.INSTANCE.setMainRunway(Runway.FromXML(file));
            } catch (JAXBException e1) {
                RootController.showAlert("Incorrect XML Format. Please use an Exported XML Runway");
            } catch (Exception e2) {
                System.out.println("Other error importing runway");
                RootController.showAlert("Other error importing runway: " + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }
    public void FileMenu_ExportRunway_XML(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Runway");
        fileChooser.setInitialDirectory(new File(App.RunwaysPath));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML", "*.xml"));
        fileChooser.setInitialFileName("exported-runway.xml");
        File file = fileChooser.showSaveDialog(App.INSTANCE.stage);

        if (file != null) {
            try {
                Runway.ToXML(file, RunwayToolController.INSTANCE.mainRunway);
            } catch (Exception e1) {
                RootController.showAlert(e1.toString());
            }
        }
    }
    //#endregion



    //#region View Exporting
    public void ViewMenu_ExportAsPNG(ActionEvent e) throws IOException {
        Node runway = RunwayToolController_View.INSTANCE.topView.getCenter();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Runway");

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PNG", "*.png"));
        fileChooser.setInitialFileName("exported-runway-view.png");

        File file = fileChooser.showSaveDialog(RunwayToolController_View.INSTANCE.topView.getScene().getWindow());
        if (file == null)
            return;

        Exporter exp = new Exporter(runway, file);
        exp.saveImage(RunwayToolController.INSTANCE.exportType);
    }
    public void ViewMenu_ExportAsJPG(ActionEvent e) throws IOException {
        Node runway = RunwayToolController_View.INSTANCE.topView.getCenter();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Runway");

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JPG", "*.jpg"));
        fileChooser.setInitialFileName("exported-runway-view.jpg");

        File file = fileChooser.showSaveDialog(RunwayToolController_View.INSTANCE.topView.getScene().getWindow());
        if (file == null)
            return;

        Exporter exp = new Exporter(runway, file);
        exp.saveImage(RunwayToolController.INSTANCE.exportType);
    }
    public void ViewMenu_ExportAsGIF(ActionEvent e) throws IOException {
        Node runway = RunwayToolController_View.INSTANCE.topView.getCenter();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Runway");

        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("GIF", "*.gif"));
        fileChooser.setInitialFileName("exported-runway-view.gif");

        File file = fileChooser.showSaveDialog(RunwayToolController_View.INSTANCE.topView.getScene().getWindow());
        if (file == null)
            return;

        Exporter exp = new Exporter(runway, file);
        exp.saveImage(RunwayToolController.INSTANCE.exportType);
    }
    //#endregion



    //#region Obstacle XML
    public void ObstacleMenu_ExportXML(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Obstacle");
        fileChooser.setInitialDirectory(new File(App.ObstaclesPath));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML", "*.xml"));
        fileChooser.setInitialFileName("exported-obstacle.xml");

        File file = fileChooser.showSaveDialog(RunwayToolController_View.INSTANCE.topView.getScene().getWindow());
        if (file != null) {
            try {
                FileWriter fw = new FileWriter(file);
                fw.write(Obstacle.ToXML(RunwayToolController.INSTANCE.mainRunway.getObstacle()));
                fw.close();

            } catch (Exception e2) {
                RootController.showAlert(e2.toString());
            }

        }
    }
    public void ObstacleMenu_ImportXML(ActionEvent e) throws IOException {
        // Setup the File Chooser Window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.setInitialDirectory(new File(App.ObstaclesPath));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(App.INSTANCE.stage);

        if (selectedFile != null) {
            try {
                RunwayToolController.INSTANCE.mainRunway.setObstacle(Obstacle.FromXML(selectedFile));
                RunwayToolController.INSTANCE.setObstacle(RunwayToolController.INSTANCE.mainRunway.getObstacle());
            } catch (JAXBException e1) {
                RootController.showAlert("Malformed XML File");
                e1.printStackTrace();
            } catch (Exception e2) {
                RootController.showAlert("Other XML file import error: " + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }
    //#endregion


    //#region 外观
    //dark mode
    public void DarkTheme (ActionEvent e) throws IOException {
        App.scene.getStylesheets().clear();
        App.scene.getStylesheets().add(getClass().getResource("ThemeCss/Dark.css").toExternalForm());
    }
    //light mode
    public void LightTheme (ActionEvent e) throws IOException {
        App.scene.getStylesheets().clear();
        App.scene.getStylesheets().add(getClass().getResource("ThemeCss/Light.css").toExternalForm());
    }
    //highcontrast
    public void HighContrastTheme (ActionEvent e) throws IOException {
        App.scene.getStylesheets().clear();
        App.scene.getStylesheets().add(getClass().getResource("ThemeCss/HighContrast.css").toExternalForm());
    }
    //initial mode
    public void InitialTheme (ActionEvent e) throws IOException {
        App.scene.getStylesheets().clear();
        App.scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
    } 
    //#endregion

    //#region Help
    public void OpenGitHubRepository (ActionEvent e) throws IOException {
        App.INSTANCE.getHostServices().showDocument("https://github.com/D4nDude/SEG16");
    }
    public void OpenHelpWindow (ActionEvent e) throws IOException {
        final Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.initOwner(App.INSTANCE.stage);
        helpStage.setResizable(false);

        // Setting the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/help-window/helpWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        helpStage.setScene(scene);

        helpStage.show();
    }
    //#endregion

    /**
     * Creates a Pop-Up window to display an error message
     * 
     * @param msg
     */
    public static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}