package com.example.ce316_project_test;

import com.example.ce316_project_test.db.DBConnection;
import com.example.ce316_project_test.db.Lecture;
import com.example.ce316_project_test.db.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onCreateProjectButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CE316_Createproject_window.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            Stage stage = new Stage();
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload source file");

            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                String filename = file.getAbsolutePath();

                boolean ret = Compiler.getInstance().compile(filename);
                System.out.println(ret);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onProjectSaveButtonClick() {
        try {
            String name = "";
            String description = "";

            Project project = new Project();
            project.setName(name);
            project.setDescription(description);

            DBConnection.getInstance().insertProject(project);

            Lecture lecture = new Lecture();
            DBConnection.getInstance().insertLecture(lecture);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}