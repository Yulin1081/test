package uk.ac.soton.SEG16;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import uk.ac.soton.SEG16.runway.TOL;
import uk.ac.soton.SEG16.RunwayToolController.RunwayView;
import uk.ac.soton.SEG16.runway.RunwaySide;
import uk.ac.soton.SEG16.runway.RunwayViewController;

public class RunwayToolController_View {

    // SINGLETON
    public static RunwayToolController_View INSTANCE;
    // SINGLETON

    public Rectangle clipRec = new Rectangle();

    @FXML
    public BorderPane topView;

    @FXML
    public RadioButton topViewToggle, sideViewToggle;

    RunwayViewController runwayViewController = null;

    @FXML
    public void initialize() {
        // Setup Singleton
        INSTANCE = this;

        clipRec.setHeight(440);
        clipRec.setWidth(440);
        topView.setClip(clipRec);

        //Pannable canvas
        pannableCanvas = new PannableCanvas(0);
        scrollPane = new ScrollPane();
    }

    // #region create a zoomable, pannable canvas
    PannableCanvas pannableCanvas;
    ScrollPane scrollPane;
    public void setPannableCanvas(Pane viewToAdd) {
        
        if(viewToAdd == null){
            FXMLLoader loader;
            if(sideViewToggle.isSelected()){
                loader = new FXMLLoader(getClass().getResource("sideView.fxml"));
            }
            else{
                loader = new FXMLLoader(getClass().getResource("topView.fxml"));
            }
            try {
                viewToAdd = loader.load();
            }
            catch(Exception exception){
                RootController.showAlert("Failed to load pannable canvas");
                exception.printStackTrace();
                return;
            }
        }

        PannableCanvas pannableCanvas = new PannableCanvas(viewToAdd.getWidth(), viewToAdd.getHeight(), rotationButton.isSelected() ? rotation : 0);
        pannableCanvas.getChildren().add(viewToAdd);

        SceneGestures sceneGestures = new SceneGestures(pannableCanvas);
        scrollPane = new ScrollPane(pannableCanvas);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        topView.setCenter(scrollPane);
    }
    // #endregion

    /**
     * Set the RunwayView to the side view.
     * 
     * @throws IOException If the FXML loader fails.
     */
    public void toggleSideViewButton(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.runwayView = RunwayView.SideView;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/sideView.fxml"));
        Pane viewTop = loader.load();
        setPannableCanvas(viewTop);
        // topView.setCenter(viewTop); //commented out to add a pannable canvas instead, easily re-implemented if any future bugs are discovered
        sideViewToggle.setSelected(true);
        runwayViewController = loader.getController();

        updateView();
        if (rotateOrNot) {
            Rotate runwayRotate = new Rotate();
            runwayRotate.setPivotX(230);
            runwayRotate.setPivotY(215);
            runwayRotate.setAngle(undoRotate);
            topView.getTransforms().addAll(runwayRotate);
        } else {
        }
    }

    /**
     * Updates the RunwayView to the top view.
     * 
     * @throws IOException If the FXML loader fails.
     */
    public void toggleTopViewButton(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.runwayView = RunwayView.TopView;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/topView.fxml"));
        Pane viewTop = loader.load();
        setPannableCanvas(viewTop);
        // topView.setCenter(viewTop); //commented out to add a pannable canvas instead,
        // easily re-implemented if any future bugs are discovered

        topViewToggle.setSelected(true);
        runwayViewController = loader.getController();

        updateView();
        // 0.10498687664
        if (rotationButton.isSelected()) {
            if (rotateOrNot) {
                Rotate runwayRotate = new Rotate();
                runwayRotate.setPivotX(230);
                runwayRotate.setPivotY(215);
                runwayRotate.setAngle(rotation);
                topView.getTransforms().addAll(runwayRotate);
            } else {
            }
        } else {
        }
    }

    @FXML
    public CheckBox rotationButton;

    public void toggleRotation(ActionEvent e) throws IOException {
        if (topViewToggle.isSelected()) {
            rotateOrNot = true;
            rotateView();

        } else {
            rotateOrNot = true;
        }

    }

    public static int undoRotate;
    public static boolean rotateOrNot;
    public static int rotation = 0;

    public void rotateView() {
        Rotate runwayRotate = new Rotate();
        runwayRotate.setPivotX(230);
        runwayRotate.setPivotY(215);
        if (rotationButton.isSelected()) {
            rotateOrNot = true;
            if (topViewToggle.isSelected()) {
                runwayRotate.setAngle(rotation);
                topView.getTransforms().addAll(runwayRotate);
            } else {
            }
        }

        else {
            if (topViewToggle.isSelected()) {
                rotateOrNot = false;
                runwayRotate.setAngle(undoRotate);
                topView.getTransforms().addAll(runwayRotate);
            }

            else {
                rotateOrNot = false;
            }
        }

    }

    /**
     * Updates the RunwayView with the runway and forces an obstacle update.
     * 
     * @throws IOException
     */
    public void updateView() throws IOException {        
        if (RunwayToolController.INSTANCE.runwayView == null) {
            try {
                toggleTopViewButton(null);
                return; // Must return otherwise it will run updateView again
            } catch (IOException e) {
                RootController.showAlert("Error: Cannot select default view");
            }
        }

        pannableCanvas.setOrientation(rotationButton.isSelected() && topViewToggle.isSelected() ? rotation : 0);

        // Assemble the obstacle
        Obstacle obst = new Obstacle("User Defined Obstacle", Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleX.getText()),
                Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleY.getText()),
                Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleLengthInput.getText()),
                Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleWidthInput.getText()),
                Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleFrontHeightinput.getText()),
                Float.parseFloat(RunwayToolController_Imports.INSTANCE.obstacleBackHeightInput.getText()));
        RunwayToolController.INSTANCE.mainRunway.setObstacle(obst);

        // Update Display
        runwayViewController.displayvalues(RunwayToolController.INSTANCE.mainRunway);

        // Update lines
        setLines();
    }

    public void setLines(){
        Calculator calculator = new Calculator(RunwayToolController.INSTANCE.mainRunway);
        calculator.runCalculations(RunwayToolController.INSTANCE.mainRunway.getSide() == RunwaySide.L ? "left" : "right",
        RunwayToolController.INSTANCE.mainRunway.getTOL() == TOL.Landing);
        runwayViewController.setLines(calculator, RunwayToolController.INSTANCE.mainRunway);
    }
}