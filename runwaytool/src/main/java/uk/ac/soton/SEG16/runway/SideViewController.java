package uk.ac.soton.SEG16.runway;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;
import uk.ac.soton.SEG16.Calculator;
import uk.ac.soton.SEG16.Obstacle;
import javafx.scene.transform.Rotate;

public class SideViewController extends RunwayViewController {

    /** Main BoarderPane for the View? */ // TODO: update desc.
    @FXML
    BorderPane sideViewBorderPane;

    @FXML
    Polygon gradedArea;

    @FXML
    Line tocs;

    @FXML ImageView arrow;

    @FXML Polygon clearedArea, stopway;

    @FXML
    GridPane runwayHeight;

    @FXML
    Line ldaline1, toraline, asdaline, todaline, resaline, sixtyM, displacedThresholdLine;

    @FXML
    Label ldalabel1, toralabel, asdalabel, todalabel, resalabel1, sixtyMlabel, displacedThresholdLabel;

    @FXML
    HBox ldaResaSixtyM, hboxLdaSixtyResalbls;

    @Override
    public void displayvalues(Runway runway) {

        Float scale = (400 / ((float) runway.getTORA()));

        int clearedAreaLength = runway.getTODA() - runway.getTORA();
        int stopwayAreaLength = runway.getASDA() - runway.getTORA();

       

    



        // if ((200 + (60 * scale)) > 220) {
        //     gradedArea.getPoints().addAll(new Double[] {

        //             (double) -220, (double) 13,
        //             (double) 220, (double) 13,

        //             (double) 220, (double) -13,
        //             (double) -220, (double) -13

        //     });

            

        // } else {
        //     gradedArea.getPoints().addAll(new Double[] {
        //             (double) -200 - (60 * scale), (double) 13,
        //             (double) 200 + (60 * scale), (double) 13,

        //             (double) 200 + (60 * scale), (double) -13,
        //             (double) -200 - (60 * scale), (double) -13,
        //     }
        //     );
        // }

         // moveRunway
         if (runway.getMainDesignator() < 18) {
            

            displacedThresholdLine.setStartX(0);
            displacedThresholdLine.setEndX(runway.getThreshold() * scale);
            displacedThresholdLine.setTranslateX(0);

            displacedThresholdLabel.setText("Dispalced Threshold " + runway.getThreshold() + "m");
            displacedThresholdLabel.setTranslateX(0);

            clearedArea.getPoints().clear();
        clearedArea.getPoints().addAll(new Double[] {

            (double) -200 , (double) 13,
            (double) 200 + ((clearedAreaLength * scale)/2), (double) 13,

            (double) 200 + ((clearedAreaLength * scale)/2), (double) -13,
            (double) -200, (double) -13

    });
    stopway.getPoints().clear();
        stopway.getPoints().addAll(new Double[] {

            (double) -200 , (double) 13,
            (double) 200 + ((stopwayAreaLength * scale)/2), (double) 13,

            (double) 200 + ((stopwayAreaLength * scale)/2), (double) -13,
            (double) -200 , (double) -13

    });

    runwayHeight.setTranslateX(-20);
            clearedArea.setTranslateX(-20 + ((clearedAreaLength * scale))/4);
            stopway.setTranslateX(-20 + ((clearedAreaLength * scale))/4);
            
            
        } else {
            
            displacedThresholdLine.setStartX(440 - (runway.getThreshold() * scale));
            displacedThresholdLine.setEndX(440);
            displacedThresholdLine.setTranslateX(437 - (runway.getThreshold() * scale));

            displacedThresholdLabel.setText("Dispalced Threshold " + runway.getThreshold() + "m");
            displacedThresholdLabel.setTranslateX(260);

            clearedArea.getPoints().clear();
            clearedArea.getPoints().addAll(new Double[] {
    
                (double) -200 - ((clearedAreaLength * scale)/2), (double) 13,
                (double) 200 , (double) 13,
    
                (double) 200 , (double) -13,
                (double) -200 - ((clearedAreaLength * scale)/2), (double) -13
    
        });
        stopway.getPoints().clear();
            stopway.getPoints().addAll(new Double[] {
    
                (double) -200 - ((stopwayAreaLength * scale)/2), (double) 13,
                (double) 200 , (double) 13,
    
                (double) 200 , (double) -13,
                (double) -200 - ((stopwayAreaLength * scale)/2), (double) -13
    
        });

        runwayHeight.setTranslateX(20);
            clearedArea.setTranslateX(20 - ((clearedAreaLength * scale))/4);
            stopway.setTranslateX(20 - ((clearedAreaLength * scale))/4);
            
            
        }

        // Get obstacle from runway
        Obstacle obst = runway.getObstacle();

        // Set runway markings
        if (runway.getMainDesignator() < 18) {
            leftNumber.setText(runway.getMainDesignatorString());
            rightNumber.setText(runway.getSecondaryDesignatorString());
            leftCharacter.setText(runway.getMainSide());
            rightCharacter.setText(runway.getSecondarySide());
        } else {
            leftNumber.setText(runway.getSecondaryDesignatorString());
            rightNumber.setText(runway.getMainDesignatorString());
            leftCharacter.setText(runway.getSecondarySide());
            rightCharacter.setText(runway.getMainSide());
        }

        // setup obstacle
        obstacle.getPoints().clear();
        if (obst != null) {

            // Setup obstacle values
            Float obstX = obst.getDistanceX() * scale;
            Float length = obst.getLength() * scale;
            Float leftHeight = (float) obst.getFrontHeight() * scale;
            Float rightHeight = (float) obst.getBackHeight() * scale;

            if (runway.getMainDesignator() < 18) {
                obstX = obstX - 220;

            } else {
                obstX = 220 - obstX;
                leftHeight = (float) obst.getBackHeight() * scale;
                rightHeight = (float) obst.getFrontHeight() * scale;
            }

            // Setup obstacle Polygon
            Translate translate = new Translate();
            translate.setX(obstX);

            obstacle.getPoints().addAll(new Double[] {
                    (double) (-length / 2), 10.0,
                    (double) (length / 2), 10.0,
                    (double) (length / 2), (double) -(rightHeight - 10),
                    (double) (-length / 2), (double) -(leftHeight - 10),
            });
            obstacle.getTransforms().setAll(translate);
        }

        // TOCS and ALS reigon

        float maxHeight = Math.max((float) obst.getFrontHeight() * scale, (float) obst.getBackHeight() * scale);
        //float minHeight = Math.max((float) obst.getFrontHeight() * scale, (float) obst.getBackHeight() * scale);
        Float obstX = obst.getDistanceX() * scale;
        boolean obstMaxSide = getMaxSide(obst.getFrontHeight() * scale, obst.getBackHeight() * scale);
        //boolean obstMaxSideFlipped = getMaxSide(obst.getBackHeight() * scale, obst.getFrontHeight() * scale);

        // Landing line calculations

        // landing over the object left side
        if ((runway.getMainDesignator() < 18 && ((obstX / scale) < ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() == "Landing"))) {

            tocs.setStartX(0);

            // front side taller
            if (obstMaxSide) {
                // Resa is less than landing point
                if ( (((double) obstX - ((obst.getLength() / 2) * scale) + 20 + ((maxHeight * (50))))) > ((runway.getRESA() * scale) + 20) ) {
                    tocs.setStartY(110
                            - (maxHeight + ((obstX - ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX((double) obstX - ((obst.getLength() / 2) * scale) + 20 + (60 * scale)
                            + ((maxHeight * (50))));
                }
                // Resa is greater than landing point
                else {
                    tocs.setStartY(110 - (((obstX - ((obst.getLength() / 2) * scale) + 20 + (runway.getRESA() * scale))
                            + (60 * scale)) / (50)));
                    tocs.setEndX((double) obstX - ((obst.getLength() / 2) * scale) + 20 + (60 * scale)
                            + (runway.getRESA() * scale));
                }
            }
            // back side taller
            else {
                // Resa is less than landing point
                if ((obstX + 20 + ((obst.getLength() / 2) * scale) + ((maxHeight * (50)))) > ((runway.getRESA() * scale)
                        + 20)) {
                    tocs.setStartY(110 - (maxHeight + ((obstX + ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX((double) obstX + 20 + (60 * scale) + ((obst.getLength() / 2) * scale)
                            + ((maxHeight * (50))));
                }
                // Resa is greater than landing point
                else {
                    tocs.setStartY(110 - (((obstX + ((obst.getLength() / 2) * scale) + 20 + (60 * scale))
                            + (runway.getRESA() * scale)) / (50)));
                    tocs.setEndX((double) obstX + 20 + (60 * scale) + ((obst.getLength() / 2) * scale)
                            + (runway.getRESA() * scale));
                }

            }
            tocs.setEndY(110);

            // landing over the object right side
        } else if (((runway.getMainDesignator() > 18 && ((obstX / scale) < ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() == "Landing")))) {
            tocs.setStartX(440);
            // front side taller
            if (obstMaxSide) {
                // Resa is less than landing point
                if ((((double) 440 - obstX + (obst.getLength() / 2) - 20 - ((maxHeight * (50))))) < (440 - 20
                        - (runway.getRESA() * scale))) {
                    // tocs.setStartY(110 - (maxHeight + ((obstX - ((obst.getLength()/2) * scale) +
                    // 20 + (60 * scale))/(50))));
                    // tocs.setStartY(110 -(maxHeight + ((obstX + (obst.getLength()/2) + 20 + (60 *
                    // scale))/(50))) );
                    tocs.setStartY(110
                            - (maxHeight + ((obstX - ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX(
                            (double) 440 - obstX + (obst.getLength() / 2) - 20 - (60 * scale) - ((maxHeight * (50))));
                }
                // Resa is greater than landing point
                else {
                    tocs.setStartY(110 - (((obstX - ((obst.getLength() / 2) * scale) + 20 + (60 * scale))
                            + (runway.getRESA() * scale)) / (50)));
                    tocs.setEndX((double) 440 - obstX + (obst.getLength() / 2) - 20 - (60 * scale)
                            - (runway.getRESA() * scale));
                }
            }
            // back side taller
            else {
                // Resa is less than landing point
                if ((double) 440 - obstX - (obst.getLength() / 2) - 20
                        - ((maxHeight * (50))) < (440 - 20 - (runway.getRESA() * scale))) {
                    tocs.setStartY(110
                            - (maxHeight + ((obstX + ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX(
                            (double) 440 - obstX - (obst.getLength() / 2) - 20 - (60 * scale) - ((maxHeight * (50))));
                }
                // Resa is less than landing point
                else {
                    tocs.setStartY(110 - (((obstX + ((obst.getLength() / 2) * scale) + 20 + (60 * scale))
                            + (runway.getRESA() * scale)) / (50)));
                    tocs.setEndX((double) 440 - obstX - (obst.getLength() / 2) - 20 - (60 * scale)
                            - (runway.getRESA() * scale));
                }
            }
            tocs.setEndY(110);
        }

        // landing towards left side
        else if ((runway.getMainDesignator() < 18 && ((obstX / scale) > ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() == "Landing"))) {
            tocs.setStartX(0);
            tocs.setStartY(110 - ((300 / 50) * scale));
            tocs.setEndX(20);
            tocs.setEndY(110);
        }
        // landing towards right side
        else if ((runway.getMainDesignator() > 18 && ((obstX / scale) > ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() == "Landing"))) {
            tocs.setStartX(440);
            tocs.setStartY(110 - ((300 / 50) * scale));
            tocs.setEndX(420);
            tocs.setEndY(110);
        }
        // TakeOff Line calculations

        // Take off away from obstacle left side
        else if ((runway.getMainDesignator() < 18 && ((obstX / scale) < ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() != "Landing"))) {

            tocs.setStartX(440);
            tocs.setStartY(110 - (40) * (scale/50));
            tocs.setEndX(400);
            tocs.setEndY(110);

            // Take off away from obstacle right side
        } else if ((runway.getMainDesignator() > 18 && ((obstX / scale) < ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() != "Landing"))) {
            tocs.setStartX(0);
            tocs.setStartY(110 - ((40 * (scale / 50))));
            tocs.setEndX(40);
            tocs.setEndY(110);
        }
        // Take off over from obstacle left side
        else if ((runway.getMainDesignator() < 18 && ((obstX / scale) > ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() != "Landing"))) {
            tocs.setStartX(440);
            // front side taller
            if (obstMaxSide) {
                // take off line less than Resa
                if ((440 - ((runway.getTORA() * scale) - obstX) + (obst.getLength() / 2) - 20
                        - ((maxHeight * (50)))) < (440 - (20 + (runway.getRESA() * scale)))) {
                    tocs.setStartY(110 - (maxHeight
                            + ((((runway.getTORA() * scale) - obstX) + (obst.getLength() / 2) + 20 + (60 * scale))
                                    / (50))));
                    tocs.setEndX((double) 440 - ((runway.getTORA() * scale) - obstX) - (obst.getLength() / 2) - 20
                            - (60 * scale) - ((maxHeight * (50))));
                }
                // take off line greater than Resa
                else {
                    tocs.setStartY(110 - (((((runway.getTORA() * scale) - obstX) + ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale)) + (runway.getRESA() * scale)) / (50)));
                    tocs.setEndX((double) 440 - ((runway.getTORA() * scale) - obstX) - (obst.getLength() / 2) - 20
                            - (60 * scale) - (runway.getRESA() * scale));
                }
            }
            // back side taller
            else {
                // take off line less than Resa
                if ((440 - ((runway.getTORA() * scale) - obstX) - (obst.getLength() / 2) - 20
                        - ((maxHeight * (50)))) < (440 - (20 + (runway.getRESA() * scale)))) {
                    tocs.setStartY(110 - (maxHeight
                            + ((((runway.getTORA() * scale) - obstX) - (obst.getLength() / 2) + 20) / (50))));
                    tocs.setEndX((double) 440 - ((runway.getTORA() * scale) - obstX) + (obst.getLength() / 2) - 20
                            - ((maxHeight * (50))));
                } else {
                    tocs.setStartY(110 - (((((runway.getTORA() * scale) - obstX) - ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale)) + (runway.getRESA() * scale)) / (50)));
                    tocs.setEndX((double) 440 - ((runway.getTORA() * scale) - obstX) + (obst.getLength() / 2) - 20
                            - (60 * scale) - (runway.getRESA() * scale));
                }
            }

            tocs.setEndY(110);
        }
        // Take off over from obstacle right side
        else if ((runway.getMainDesignator() > 18 && ((obstX / scale) > ((float) runway.getTORA()) / 2))
                && ((runway.getTOL().toString() != "Landing"))) {
            tocs.setStartX(0);

            // front side taller
            if (obstMaxSide) {
                // take off line less than Resa
                if (((double) ((runway.getTORA() * scale) - obstX) - ((obst.getLength() / 2) * scale) + 20
                        + ((maxHeight * (50)))) > (20 + (runway.getRESA() * scale))) {
                    tocs.setStartY(110 - (maxHeight + ((((runway.getTORA() * scale) - obstX)
                            + ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX((double) ((runway.getTORA() * scale) - obstX) + ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale) + ((maxHeight * (50))));
                }
                // take off line greater than Resa
                else {
                    tocs.setStartY(110 - (((((runway.getTORA() * scale) - obstX) + ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale) + (runway.getRESA() * scale)) / (50))));
                    tocs.setEndX((double) ((runway.getTORA() * scale) - obstX) + ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale) + (runway.getRESA() * scale));
                }

            }
            // back side taller
            else {
                // take off line less than Resa
                if (((runway.getTORA() * scale) - obstX) + 20 - ((obst.getLength() / 2) * scale)
                        + ((maxHeight * (50))) > (20 + (runway.getRESA() * scale))) {
                    // tocs.setStartY(110 - (maxHeight + ((((runway.getTORA() * scale) - obstX)
                    // + ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    
                    tocs.setStartY(110 - (maxHeight + ((((runway.getTORA() * scale) - obstX)
                            - ((obst.getLength() / 2) * scale) + 20 + (60 * scale)) / (50))));
                    tocs.setEndX((double) ((runway.getTORA() * scale) - obstX) + 20 + (60 * scale)
                            - ((obst.getLength() / 2) * scale) + ((maxHeight * (50))));
                }
                // take off line greater than Resa
                else {
                    tocs.setStartY(110 - (((((runway.getTORA() * scale) - obstX) - ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale) + (runway.getRESA() * scale)) / (50))));
                    tocs.setEndX((double) ((runway.getTORA() * scale) - obstX) - ((obst.getLength() / 2) * scale) + 20
                            + (60 * scale) + (runway.getRESA() * scale));
                }

            }
            tocs.setEndY(110);
        }

        // end TOCS reigon

        // Rotate runway arrow if necessary
        Rotate rotate = new Rotate();
        rotate.setPivotX(65);
        rotate.setPivotY(5);

        if (runway.getMainDesignator() < 18) {
            rotate.setAngle(0);
        } else {
            rotate.setAngle(180);
        }
        directionTakeOffLanding.setText(runway.getTOL().toString() + " direction");

        arrow.getTransforms().setAll(rotate);
    }

    @Override
    public void setLines(Calculator calc, Runway runway) {
        int offset = 20;
        Obstacle obs = runway.getObstacle();
        double obstacleOffsetLength = (offset + keepAspect(obs.getDistanceX() + obs.getLength(), runway));
        
        ldaResaSixtyM.getChildren().clear();

        if (runway.getTOL() == TOL.Landing) {
            ldaline1.setOpacity(1);
            resaline.setOpacity(1);
            sixtyM.setOpacity(1);
            ldalabel1.setOpacity(1);
            resalabel1.setOpacity(1);
            sixtyMlabel.setOpacity(1);

            toraline.setOpacity(0);
            todaline.setOpacity(0);
            asdaline.setOpacity(0);
            toralabel.setOpacity(0);
            todalabel.setOpacity(0);
            asdalabel.setOpacity(0);
        } else {
            ldaline1.setOpacity(0);
            resaline.setOpacity(0);
            sixtyM.setOpacity(0);
            ldalabel1.setOpacity(0);
            resalabel1.setOpacity(0);
            sixtyMlabel.setOpacity(0);

            toraline.setOpacity(1);
            todaline.setOpacity(1);
            asdaline.setOpacity(1);
            toralabel.setOpacity(1);
            todalabel.setOpacity(1);
            asdalabel.setOpacity(1);
        }

        //#region setting labels and line lengths
        ldaline1.setEndX(keepAspect(calc.getLDA(), runway));
        ldalabel1.setText("LDA: " + calc.getLDA() + "M");
        toraline.setEndX(keepAspect(calc.getTORA(), runway));
        toralabel.setText("TORA: " + calc.getTORA() + "M");
        todaline.setEndX(keepAspect(calc.getTODA(), runway));
        todalabel.setText("TODA: " + calc.getTODA() + "M");
        asdaline.setEndX(keepAspect(calc.getASDA(), runway));
        asdalabel.setText("ASDA: " + calc.getASDA() + "M");
        sixtyM.setEndX(keepAspect(60, runway));
        resaline.setEndX(keepAspect(runway.getRESA(), runway));
        resalabel1.setText("RESA: " + runway.getRESA() + "M");
        //#endregion

        // obstacle is less than half the runway
        boolean obstaclelessThanHalf = obs.getDistanceX() <= runway.getTORA() / 2;
        boolean designatorLessThan18 = runway.getMainDesignator() < 18;
        if (!(obstaclelessThanHalf ^ designatorLessThan18)) {
            // RESA -> 60 -> LDA
            //#region re-ordereing resa, lda and 60m
            ldaResaSixtyM.getChildren().add(resaline);
            ldaResaSixtyM.getChildren().add(sixtyM);
            ldaResaSixtyM.getChildren().add(ldaline1);

            hboxLdaSixtyResalbls.getChildren().clear();
            hboxLdaSixtyResalbls.getChildren().add(resalabel1);
            hboxLdaSixtyResalbls.getChildren().add(sixtyMlabel);
            hboxLdaSixtyResalbls.getChildren().add(ldalabel1);
            //#endregion

            Insets obstacleOffset = new Insets(0, 0, 0, 0);

            if (obstaclelessThanHalf && designatorLessThan18) { //obstacle is under half the runway and designator is under than 18
                obstacleOffset = new Insets(0, 0, 0, obstacleOffsetLength); 
                ldaResaSixtyM.setAlignment(Pos.TOP_LEFT);
                GridPane.setMargin(ldaResaSixtyM, obstacleOffset);
                HBox.setMargin(ldaline1, new Insets(0, 0, 0, tocs.getEndX() - obstacleOffsetLength - resaline.getEndX() - sixtyM.getEndX()));
                GridPane.setMargin(hboxLdaSixtyResalbls, obstacleOffset);             
            } 
            else if (!obstaclelessThanHalf && !designatorLessThan18) { //obstacle is over half the runway and designator is over 18
                ldaResaSixtyM.setAlignment(Pos.TOP_RIGHT);
                Insets ldaMargin  = new Insets(0, 440 - tocs.getEndX(), 0, 0);
                HBox.setMargin(ldaline1, null);
                GridPane.setMargin(ldaResaSixtyM, ldaMargin);
                obstacleOffset = new Insets(0, 0, 0, tocs.getEndX()); 
                GridPane.setHalignment(hboxLdaSixtyResalbls, HPos.RIGHT);
                GridPane.setMargin(hboxLdaSixtyResalbls, ldaMargin);
            }

            GridPane.setHalignment(toraline, HPos.LEFT);
            GridPane.setHalignment(todaline, HPos.LEFT);
            GridPane.setHalignment(asdaline, HPos.LEFT);

            GridPane.setMargin(toraline, obstacleOffset);
            GridPane.setMargin(todaline, obstacleOffset);
            GridPane.setMargin(asdaline, obstacleOffset);

            GridPane.setMargin(toralabel, obstacleOffset);
            GridPane.setMargin(todalabel, obstacleOffset);
            GridPane.setMargin(asdalabel, obstacleOffset);
        } 
        else if (obstaclelessThanHalf ^ designatorLessThan18) {
            //LDA -> 60 -> RESA
            //#region re-ordering resa, lda, 60m
            ldaResaSixtyM.getChildren().add(ldaline1);
            ldaResaSixtyM.getChildren().add(sixtyM);
            ldaResaSixtyM.getChildren().add(resaline);

            hboxLdaSixtyResalbls.getChildren().clear();
            hboxLdaSixtyResalbls.getChildren().add(ldalabel1);
            hboxLdaSixtyResalbls.getChildren().add(sixtyMlabel);
            hboxLdaSixtyResalbls.getChildren().add(resalabel1);
            //#endregion

            Insets margin = new Insets(0, 0, 0, 0);
            if (obstaclelessThanHalf && !designatorLessThan18) {//obstacle is under half the runway and designator is over 18
                double newOffset = obstacleOffsetLength + resaline.getEndX() + sixtyM.getEndX();
                double var = obs.getFrontHeight() > obs.getBackHeight() ? obs.getLength()*2 : 0;
                Insets margin1 = new Insets(0, newOffset - var, 0, 0); 
                ldaResaSixtyM.setAlignment(Pos.TOP_RIGHT);
                GridPane.setMargin(ldaResaSixtyM, margin1);
                
                HBox.setMargin(ldaline1, new Insets(0, 440 - tocs.getEndX() - newOffset, 0, 0));

                GridPane.setHalignment(toraline, HPos.RIGHT);
                GridPane.setHalignment(todaline, HPos.RIGHT);
                GridPane.setHalignment(asdaline, HPos.RIGHT);
                margin = new Insets(0, obstacleOffsetLength, 0, 0);
            }
            else if (!obstaclelessThanHalf && designatorLessThan18) {//obstacle is over half the runway and designator under than 18
                HBox.setMargin(ldaline1, null); //sets margin to 0 all around incase other cases have been run before
                margin = new Insets(0, 0, 0, offset);  
                ldaResaSixtyM.alignmentProperty().set(Pos.TOP_LEFT);
                GridPane.setMargin(ldaResaSixtyM, new Insets(0,0,0 ,tocs.getEndX())); //set the margin to the tocs line
                GridPane.setHalignment(todaline, HPos.LEFT);
                GridPane.setHalignment(toraline, HPos.LEFT);
                GridPane.setHalignment(asdaline, HPos.LEFT);
            }

            GridPane.setMargin(hboxLdaSixtyResalbls, margin); 
            GridPane.setMargin(toraline, margin);
            GridPane.setMargin(todaline, margin);
            GridPane.setMargin(asdaline, margin);

            GridPane.setMargin(toralabel, margin);
            GridPane.setMargin(todalabel, margin);
            GridPane.setMargin(asdalabel, margin);
        }
    }

    // keeps the value of a line within the length of the screen keeping aspect
    // ration
    private double keepAspect(double value, Runway runway) {
        Float scale = (400 / ((float) runway.getTORA()));
        return value * scale;
    }

    public boolean getMaxSide(float frontH, float backH) {
        if (Math.max(frontH, backH) == frontH) {
            return true;
        } else {
            return false;
        }
    }
}
