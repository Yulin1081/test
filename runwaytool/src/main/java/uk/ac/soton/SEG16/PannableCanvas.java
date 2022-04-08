package uk.ac.soton.SEG16;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.*;


class PannableCanvas extends Pane{

    DoubleProperty scale = new SimpleDoubleProperty(1.0);
    int orientation;

    public PannableCanvas(int orientation){
        setPrefSize(600,600);
        setStyle("-fx-background-color: white;");
        this.orientation = orientation;
        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }
    
    public PannableCanvas(Double width, Double height){
        setPrefSize(width, height);
        setStyle("-fx-background-color: white;");
        orientation = 0;
        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    public PannableCanvas(Double width, Double height, int orientation){
        setPrefSize(width, height);
        setStyle("-fx-background-color: white;");
        this.orientation = orientation;
        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    public void addGrid(){
        double width = getBoundsInLocal().getWidth();
        double height = getBoundsInLocal().getHeight();

        Canvas grid = new Canvas(width, height);
        grid.setMouseTransparent(true);

        getChildren().add(grid);
        grid.toBack();
    }

    public double getScale(){
        return scale.get();
    }

    public void setScale(double scale){
        this.scale.set(scale);
    }

    public void setPivot(double x, double y){
        setTranslateX(x);
        setTranslateY(y);
    }

    public int getOrientation(){
        return orientation;
    }
    public void setOrientation(int rotation){
        this.orientation = rotation;
    }
}

/**
 * Mouse drag context used for scene and nodes.
 */
class DragContext {
    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

    int rotation;
}

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
class SceneGestures {

    private static final double maxScale = 10.0d;
    private static final double minScale = 1.0d;

    private DragContext sceneDragContext = new DragContext();

    PannableCanvas canvas;

    public SceneGestures( PannableCanvas canvas) {
        this.canvas = canvas;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // right mouse button => panning
            if( !event.isSecondaryButtonDown())
                return;

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = canvas.getTranslateX();
            sceneDragContext.translateAnchorY = canvas.getTranslateY();

            sceneDragContext.rotation = canvas.getOrientation();

        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // right mouse button => panning
            if( !event.isSecondaryButtonDown())
                return;
                              
            if(canvas.getScale() == minScale)
            {
                canvas.setTranslateX(0);
                canvas.setTranslateY(0);
            }
            else{
                // Rotate rotate = new Rotate(canvas.getOrientation()); 
                Rotate rotate = new Rotate(-sceneDragContext.rotation);
                double[] vector = new double [] {sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX, sceneDragContext.translateAnchorY + event.getSceneY()- sceneDragContext.mouseAnchorY };

                Point2D coord = rotate.transform(vector[0], vector[1]);

                canvas.setTranslateX(coord.getX());
                canvas.setTranslateY(coord.getY() );
            }
            event.consume();
        }
    };


    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = bound( scale, minScale, maxScale);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));

            canvas.setScale( scale);

            // note: pivot value must be untransformed, i. e. without scaling
            canvas.setPivot(f*dx, f*dy);
            event.consume();
        }

    };

    public static double bound( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
