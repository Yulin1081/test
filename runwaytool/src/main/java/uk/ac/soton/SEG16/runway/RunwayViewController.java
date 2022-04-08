package uk.ac.soton.SEG16.runway;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import uk.ac.soton.SEG16.Calculator;

public abstract class RunwayViewController {
    
    /** The Left Runway number (relative to the view). */
    @FXML 
    protected Label leftNumber;

    /** The Left Runway Character (relative to the view)*/
    @FXML 
    protected Label leftCharacter;

    /** The Right Runway number (relative to the view). */
    @FXML
    protected Label rightNumber;

    /** The Right Runway Character (relative to the view)*/
    @FXML
    protected Label rightCharacter;

    /** The runway obstacle polygon */
    @FXML
    protected Polygon obstacle;

    /** Takeoff Direction arrow */
    @FXML
    protected ImageView arrow;

    /** Label for the takeoff/landing direction arrow */
    @FXML 
    protected Label directionTakeOffLanding;

    //public abstract void update();

    /**
     * Update the runway with the correct values.
     * @param runway Runway to display
     */
    public abstract void displayvalues(Runway runway);

    /**
     * update the line lengths for each view
     * @param calc Calculator to get the correct lengths
     */
    public abstract void setLines(Calculator calc, Runway runway);
}
