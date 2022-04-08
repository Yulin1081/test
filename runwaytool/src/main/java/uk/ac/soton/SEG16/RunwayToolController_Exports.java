package uk.ac.soton.SEG16;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RunwayToolController_Exports {

    // SINGLETON
    public static RunwayToolController_Exports INSTANCE;
    // SINGLETON

    @FXML
    public Label newTORA, newTODA, newASDA, newThreshold, newLDA;

    @FXML
    public Label calculationMessage, verdictMessage;
    @FXML
    public Label toraCalculation, todaCalculation, asdaCalculation, thresholdCalculation, ldaCalculation;

    @FXML
    public void initialize() {
        // Setup Singleton
        INSTANCE = this;
    }
}