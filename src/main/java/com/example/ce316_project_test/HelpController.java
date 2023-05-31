package com.example.ce316_project_test;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HelpController {

    @FXML
    private Button assigmentResults;

    @FXML
    private Button createConfig;

    @FXML
    private Button createProject;

    @FXML
    private VBox createconfighelp;

    @FXML
    private Button editConfig;

    @FXML
    private VBox helpassignmentresults;

    @FXML
    private VBox helpcreateproject;

    @FXML
    private VBox helpeditconfig;

    @FXML
    private Button mainMenu;

    @FXML
    private VBox mainhelp;



    @FXML
    void helpMain() {
        mainhelp.setVisible(true);
        helpcreateproject.setVisible(false);
        helpassignmentresults.setVisible(false);
        createconfighelp.setVisible(false);
    }

    @FXML
    void helpAssignmentResults() {
        mainhelp.setVisible(false);
        helpcreateproject.setVisible(false);
        helpassignmentresults.setVisible(true);
        createconfighelp.setVisible(false);
    }

    @FXML
    void helpCreateConfig() {
        mainhelp.setVisible(false);
        helpcreateproject.setVisible(false);
        helpassignmentresults.setVisible(false);
        createconfighelp.setVisible(true);
    }

    @FXML
    void helpCreateProject() {
        mainhelp.setVisible(false);
        helpassignmentresults.setVisible(true);
        createconfighelp.setVisible(false);
        helpcreateproject.setVisible(true);
    }

    @FXML
    void helpEditConfig(ActionEvent event) {

    }


}

