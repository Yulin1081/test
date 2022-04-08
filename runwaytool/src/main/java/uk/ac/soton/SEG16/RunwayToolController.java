package uk.ac.soton.SEG16;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import uk.ac.soton.SEG16.runway.TOL;
import uk.ac.soton.SEG16.runway.RunwaySide;
import uk.ac.soton.SEG16.Exporter.ExportType;
import uk.ac.soton.SEG16.runway.Runway;

public class RunwayToolController {

    // SINGLETON
    public static RunwayToolController INSTANCE;
    // SINGLETON

    public String takeOffLanding;
    public int leftRight;
    public ExportType exportType = ExportType.JPG;
    public RunwayView runwayView = null;

    public Runway mainRunway = new Runway(0, RunwaySide.NA, TOL.TakeOff, 0, 0, 0, 0, 0, 0, 0, new Obstacle());

    @FXML private GridPane gridPane;

    enum RunwayView {
        TopView,
        SideView
    }

    @FXML
    public void initialize() {
        // Setup Singleton
        INSTANCE = this;

        try {
            FXMLLoader loader;
            Node node;

            loader = new FXMLLoader(getClass().getResource("fxmls/runwayTool_Imports.fxml"));
            node = loader.load();
            gridPane.add(node, 1, 1);

            loader = new FXMLLoader(getClass().getResource("fxmls/runwayTool_Exports.fxml"));
            node = loader.load();
            gridPane.add(node, 3, 1);

            loader = new FXMLLoader(getClass().getResource("fxmls/runwayTool_View.fxml"));
            node = loader.load();
            gridPane.add(node, 5, 1);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void setObstacle(Obstacle ob) {
        System.out.println(ob.getName() +  " " + ob.getFrontHeight() + " " + ob.getBackHeight());
        mainRunway.setObstacle(ob);
        // Override the text boxes with the new Obstacle information
        RunwayToolController_Imports.INSTANCE.obstacleX.setText(String.valueOf(ob.getDistanceX().intValue()));
        RunwayToolController_Imports.INSTANCE.obstacleY.setText(String.valueOf(ob.getDistanceY().intValue()));
        RunwayToolController_Imports.INSTANCE.obstacleLengthInput.setText(String.valueOf(ob.getLength().intValue()));
        RunwayToolController_Imports.INSTANCE.obstacleWidthInput.setText(String.valueOf(ob.getWidth().intValue()));
        RunwayToolController_Imports.INSTANCE.obstacleFrontHeightinput.setText(String.valueOf(ob.getFrontHeight().intValue()));
        RunwayToolController_Imports.INSTANCE.obstacleBackHeightInput.setText(String.valueOf(ob.getBackHeight().intValue()));
    }
    public void setMainRunway(Runway newRunway) {
        if (mainRunway.getObstacle() == null) {
            mainRunway.setObstacle(new Obstacle());
        }
        Obstacle currentObstacle = new Obstacle(
                mainRunway.getObstacle().getName(),
                mainRunway.getObstacle().getDistanceX(),
                mainRunway.getObstacle().getDistanceY(),
                mainRunway.getObstacle().getLength(),
                mainRunway.getObstacle().getWidth(),
                mainRunway.getObstacle().getFrontHeight(),
                mainRunway.getObstacle().getBackHeight());
        mainRunway = newRunway;
        RunwayToolController_Imports.INSTANCE.takeOffButton.setSelected(false);
        RunwayToolController_Imports.INSTANCE.landingButton.setSelected(false); // Reset All Radio Buttons
        switch (mainRunway.getTOL()) {
            case TakeOff:
                RunwayToolController_Imports.INSTANCE.takeOffButton.setSelected(true);
                break;
            case Landing:
                RunwayToolController_Imports.INSTANCE.landingButton.setSelected(true);
                break;
        }
        RunwayToolController_Imports.INSTANCE.leftSideButton.setSelected(false);
        RunwayToolController_Imports.INSTANCE.rightSideButton.setSelected(false);
        RunwayToolController_Imports.INSTANCE.centreButton.setSelected(false);
        RunwayToolController_Imports.INSTANCE.noSideButton.setSelected(false);
        switch (mainRunway.getSide()) {
            case L:
                RunwayToolController_Imports.INSTANCE.leftSideButton.setSelected(true);
                break;
            case R:
                RunwayToolController_Imports.INSTANCE.rightSideButton.setSelected(true);
                break;
            case C:
                RunwayToolController_Imports.INSTANCE.centreButton.setSelected(true);
                break;
            case NA:
                RunwayToolController_Imports.INSTANCE.noSideButton.setSelected(true);
                break;
        }
        RunwayToolController_Imports.INSTANCE.runwayName.setText(String.valueOf(mainRunway.getMainDesignator()));
        RunwayToolController_Imports.INSTANCE.toraInput.setText(String.valueOf(mainRunway.getTORA())); // TORA
        RunwayToolController_Imports.INSTANCE.todaInput.setText(String.valueOf(mainRunway.getTODA())); // TODA
        RunwayToolController_Imports.INSTANCE.asdaInput.setText(String.valueOf(mainRunway.getASDA())); // ASDA
        RunwayToolController_Imports.INSTANCE.thresholdInput.setText(String.valueOf(mainRunway.getThreshold())); // Threshold
        RunwayToolController_Imports.INSTANCE.ldaInput.setText(String.valueOf(mainRunway.getLDA())); // LDA
        RunwayToolController_Imports.INSTANCE.resaInput.setText(String.valueOf(mainRunway.getRESA())); // RESA
        RunwayToolController_Imports.INSTANCE.blastInput.setText(String.valueOf(mainRunway.getBlastAllowance())); // Blast Allowance
        mainRunway.setObstacle(currentObstacle);
    }
}