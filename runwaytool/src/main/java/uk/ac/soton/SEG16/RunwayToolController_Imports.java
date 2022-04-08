package uk.ac.soton.SEG16;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import uk.ac.soton.SEG16.runway.TOL;
import uk.ac.soton.SEG16.runway.RunwaySide;

public class RunwayToolController_Imports {

    // SINGLETON
    public static RunwayToolController_Imports INSTANCE;
    // SINGLETON

    @FXML
    public RadioButton takeOffButton, landingButton;
    @FXML
    public RadioButton leftSideButton, rightSideButton, noSideButton, centreButton;

    @FXML
    public TextField runwayName;

    @FXML
    public TextField toraInput, todaInput, asdaInput, thresholdInput, ldaInput, resaInput, blastInput;

    @FXML
    public TextField obstacleX, obstacleY;
    @FXML
    public TextField obstacleLengthInput, obstacleWidthInput;
    @FXML
    public TextField obstacleFrontHeightinput, obstacleBackHeightInput;

    @FXML
    public Button calculateButton;

    @FXML
    public void initialize() {
        // Setup Singleton
        INSTANCE = this;

        InputLimiter.MakePositiveInt(runwayName);
        runwayName.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setMainDesignator(SafeParse(newValue));
        });

        InputLimiter.MakePositiveInt(toraInput);
        toraInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setTORA(SafeParse(newValue));
        });

        InputLimiter.MakePositiveInt(todaInput);
        todaInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setTODA(SafeParse(newValue));
        });

        InputLimiter.MakePositiveInt(asdaInput);
        asdaInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setASDA(SafeParse(newValue));
        });

        InputLimiter.MakeInt(thresholdInput);
        thresholdInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setThreshold(SafeParse(newValue));
        });

        InputLimiter.MakeInt(ldaInput);
        ldaInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setLDA(SafeParse(newValue));
        });

        InputLimiter.MakeInt(resaInput);
        resaInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setRESA(SafeParse(newValue));
        });

        InputLimiter.MakeInt(blastInput);
        blastInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.setBlastAllowance(SafeParse(newValue));
        });

        // Obstacle Section
        InputLimiter.MakeFloat(obstacleX);
        obstacleX.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setDistanceX(SafeParse(newValue));
        });

        InputLimiter.MakeFloat(obstacleY);
        obstacleY.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setDistanceY(SafeParse(newValue));
        });

        InputLimiter.MakePositiveFloat(obstacleLengthInput);
        obstacleLengthInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setLength(SafeParse(newValue));
        });

        InputLimiter.MakePositiveFloat(obstacleWidthInput);
        obstacleWidthInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setWidth(SafeParse(newValue));
        });

        InputLimiter.MakePositiveFloat(obstacleFrontHeightinput);
        obstacleFrontHeightinput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setFrontHeight(SafeParse(newValue));
        });

        InputLimiter.MakePositiveFloat(obstacleBackHeightInput);
        obstacleBackHeightInput.textProperty().addListener((observable, oldValue, newValue) -> {
            RunwayToolController.INSTANCE.mainRunway.getObstacle().setBackHeight(SafeParse(newValue));
        });
    }

    /**
     * Safely parses a string to an integer.
     * If it fails it returns 0
     * 
     * @param values
     * @return
     */
    public int SafeParse(String value) {
        int number = 0;
        try {
            number = Integer.parseInt(value);
        } catch (Exception e) {
            //
        }
        return number;
    }

    /**
     * Performs the runway calculations
     * 
     * @throws IOException
     */
    public void calculateAction(ActionEvent e) throws IOException {

        // Throw Alert if there are any invalid entries!
        if (!InputLimiter.IsValid()) {
            RootController.showAlert("There are " + InputLimiter.InvalidFields.size() + " invalid fields");
            return;
        }

        if (RunwayToolController_View.INSTANCE.topViewToggle.isSelected()) {
            if (RunwayToolController_View.INSTANCE.rotationButton.isSelected()) {
                Rotate runwayRotate = new Rotate();
                runwayRotate.setAngle(RunwayToolController_View.undoRotate);
                runwayRotate.setPivotX(230);
                runwayRotate.setPivotY(215);
                RunwayToolController_View.INSTANCE.topView.getTransforms().addAll(runwayRotate);
                RunwayToolController_View.rotation = (Integer.parseInt(runwayName.getText()) * 10) - 90;
                RunwayToolController_View.undoRotate = -RunwayToolController_View.rotation;
                Rotate runwayRotate2 = new Rotate();
                runwayRotate2.setAngle(RunwayToolController_View.rotation);
                runwayRotate2.setPivotX(230);
                runwayRotate2.setPivotY(215);
                RunwayToolController_View.INSTANCE.topView.getTransforms().addAll(runwayRotate2);
            } else {
                RunwayToolController_View.rotation = (Integer.parseInt(runwayName.getText()) * 10) - 90;
                RunwayToolController_View.undoRotate = -RunwayToolController_View.rotation;

            }
        } else {
            RunwayToolController_View.rotation = (Integer.parseInt(runwayName.getText()) * 10) - 90;
            RunwayToolController_View.undoRotate = -RunwayToolController_View.rotation;
        }

        Calculator calculator = new Calculator(RunwayToolController.INSTANCE.mainRunway);

        calculator.runCalculations(RunwayToolController.INSTANCE.mainRunway.getSide() == RunwaySide.L ? "left" : "right",
        RunwayToolController.INSTANCE.mainRunway.getTOL() == TOL.Landing);

                RunwayToolController_Exports.INSTANCE.toraCalculation.setText(calculator.getCalcTORA());
        if ((calculator.getTORA() < 0)) {
            RunwayToolController_Exports.INSTANCE.newTORA.setText("0");

        } else {
            RunwayToolController_Exports.INSTANCE.newTORA.setText(Integer.toString(calculator.getTORA()));
        }

        RunwayToolController_Exports.INSTANCE.todaCalculation.setText(calculator.getCalcTODA());
        if ((calculator.getTORA() < 0)) {
            RunwayToolController_Exports.INSTANCE.newTODA.setText("0");
        } else {
            RunwayToolController_Exports.INSTANCE.newTODA.setText(Integer.toString(calculator.getTODA()));
        }

        RunwayToolController_Exports.INSTANCE.asdaCalculation.setText(calculator.getCalcASDA());
        if ((calculator.getASDA() < 0)) {
            RunwayToolController_Exports.INSTANCE.newASDA.setText("0");
        } else {
            RunwayToolController_Exports.INSTANCE.newASDA.setText(Integer.toString(calculator.getASDA()));
        }

        RunwayToolController_Exports.INSTANCE.thresholdCalculation.setText(calculator.getCalcThreshold());
        RunwayToolController_Exports.INSTANCE.newThreshold.setText(thresholdInput.getText().toString());

        RunwayToolController_Exports.INSTANCE.ldaCalculation.setText(calculator.getCalcLDA());
        if ((calculator.getLDA() < 0)) {
            RunwayToolController_Exports.INSTANCE.newLDA.setText("0");
        } else {
            RunwayToolController_Exports.INSTANCE.newLDA.setText(Integer.toString(calculator.getLDA()));
        }

        if (RunwayToolController.INSTANCE.mainRunway.getTOL() == TOL.TakeOff || RunwayToolController.INSTANCE.mainRunway.getTOL() == TOL.Landing) {
            RunwayToolController_Exports.INSTANCE.calculationMessage.setText("please select runway side");
        }

        else if (RunwayToolController.INSTANCE.mainRunway.getSide() == RunwaySide.L || RunwayToolController.INSTANCE.mainRunway.getSide() == RunwaySide.R) {
            RunwayToolController_Exports.INSTANCE.calculationMessage.setText("please select Take off or Landing");
        }

        else {
            RunwayToolController_Exports.INSTANCE.calculationMessage.setText("please select runway side and please select Take off or Landing");
        }
        RunwayToolController_View.INSTANCE.updateView();
    }

    public void Toggle_RS_L(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setMainRunwaySide(RunwaySide.L);
    }

    public void Toggle_RS_R(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setMainRunwaySide(RunwaySide.R);
    }

    public void Toggle_RS_C(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setMainRunwaySide(RunwaySide.C);
    }

    public void Toggle_RS_NA(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setMainRunwaySide(RunwaySide.NA);
    }

    public void Toggle_TOL_TakeOff(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setTOL(TOL.TakeOff);
    }

    public void Toggle_TOL_Landing(ActionEvent e) throws IOException {
        RunwayToolController.INSTANCE.mainRunway.setTOL(TOL.Landing);
    }
}