package uk.ac.soton.SEG16;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class HelpWindowController {

    // SINGLETON
    public static HelpWindowController INSTANCE;
    // SINGLETON

    @FXML private BorderPane borderPane;

    /**
     * 1 = inputs
     * 2 = outputs
     * 3 = viewport
     * 4 = runway-xml
     * 5 = obstacle-xml
     * maxPage = 5
     */
    public int currentPage = 0;

    @FXML public void NextPage (ActionEvent e) throws IOException {
        currentPage = currentPage + 1 > 5 ? 5 : currentPage + 1;
        UpdateCurrentPage();
    }
    @FXML public void PreviousPage (ActionEvent e) throws IOException {
        currentPage = currentPage - 1 < 1 ? 1 : currentPage - 1;
        UpdateCurrentPage();
    }
    public void UpdateCurrentPage () throws IOException {
        FXMLLoader loader = new FXMLLoader();

        switch (currentPage) {
            case 1:
                loader = new FXMLLoader(getClass().getResource("fxmls/help-window/inputs.fxml"));
                break;
            case 2:
                loader = new FXMLLoader(getClass().getResource("fxmls/help-window/outputs.fxml"));
                break;
            case 3:
                loader = new FXMLLoader(getClass().getResource("fxmls/help-window/viewport.fxml"));
                break;
            case 4:
                loader = new FXMLLoader(getClass().getResource("fxmls/help-window/runway-xml.fxml"));
                break;
            case 5:
                loader = new FXMLLoader(getClass().getResource("fxmls/help-window/obstacle-xml.fxml"));
                break;
        }
        
        borderPane.setCenter(loader.load());
    }

    @FXML
    public void initialize() {
        // Setup Singleton
        INSTANCE = this;

        try {
            currentPage = 1;
            UpdateCurrentPage();
        } catch (Exception e) { e.printStackTrace(); }
    }
}