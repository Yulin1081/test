package uk.ac.soton.SEG16;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class Exporter {

    private Node target;
    private SnapshotParameters params;
    private BufferedImage bImg;
    private File file;

    enum ExportType {
        PNG,
        JPG,
        GIF
    }
        
    /**
     * Creates the Exporter object and creates the image representation of the runway pane.
     * 
     * @param runway The runway Pane
     */
    public Exporter(Node runway, File file) {
        this.target = runway;
        this.file = file;

        // Take snapshot of the runway pane.
        params = new SnapshotParameters();
        WritableImage wImg = target.snapshot(params, null);
        bImg = SwingFXUtils.fromFXImage(wImg, null);
    }

    public void saveImage (ExportType exportType) {
        BufferedImage bImg2 = new BufferedImage(bImg.getWidth(), bImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        bImg2.createGraphics().drawImage(bImg,0 ,0, null);

        try {
            switch (exportType) {
                case PNG:
                    if (!ImageIO.write(bImg, "png", file)) RootController.showAlert("Unable to successfully save export to png.");
                    break;
                case JPG:
                    if (!ImageIO.write(bImg2, "jpg", file)) RootController.showAlert("Unable to successfully save export to jpg.");
                    break;
                case GIF:
                    if (!ImageIO.write(bImg, "gif", file)) RootController.showAlert("Unable to successfully save export to gif.");
                    break;
            }
        } catch (IOException e) {
            RootController.showAlert("Could not open the file path specified.");
            e.printStackTrace();
        }
    }
}
