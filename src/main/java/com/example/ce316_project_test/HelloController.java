package com.example.ce316_project_test;

import com.example.ce316_project_test.db.DBConnection;
import com.example.ce316_project_test.db.Lecture;
import com.example.ce316_project_test.db.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javafx.fxml.Initializable;

public class HelloController {

    @FXML
    private AnchorPane assign_anchor;

    @FXML
    private AnchorPane configuration_anchor;

    @FXML
    private AnchorPane mainanchor;

    @FXML
    private AnchorPane project_anchor;

    @FXML
    private TextField project_name_tf;

    @FXML
    private TextField project_description_tf;

    @FXML
    private TextField configuration_text;

    @FXML
    private TextField compilerpath_text;
    @FXML
    private ChoiceBox<String> language_choicebox;

    @FXML
    private Button save_button;
    @FXML
    void initialize() {

        openmainscreen();
        language_choicebox.getItems().add("python");
        language_choicebox.getItems().add("Java");
        language_choicebox.getItems().add("C");
        language_choicebox.getItems().add("C++");
    }
    @FXML
    public void create_configuration_button(){
        mainanchor.setVisible(false);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(true);
        assign_anchor.setVisible(false);
    }

    public void openmainscreen(){
        mainanchor.setVisible(true);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(false);
    }


    //------------------------------------
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Label welcomeText;
    protected String configurationStatus = "";
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onCreateProjectButtonClick() {
        try {
            mainanchor.setVisible(false);
            project_anchor.setVisible(true);
            configuration_anchor.setVisible(false);
            assign_anchor.setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void help_action(){

    }

    @FXML
    protected void save_project_settings() {
        String project_name = project_name_tf.getText();
        String project_description = project_description_tf.getText();
        System.out.println(project_name);
        System.out.println(project_description);
        Project new_project = new Project(0, project_name, project_description, 0, 0);
        DBConnection.getInstance().insertProject(new_project);

        mainanchor.setVisible(true);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(false);
    }

    @FXML
    protected void configuration_save(){
        String configuration_name_text = configuration_text.getText();
        String  compilerpath_name_text= compilerpath_text.getText();
        String Language = language_choicebox.getSelectionModel().getSelectedItem();
        System.out.println(configuration_name_text);
        System.out.println(compilerpath_name_text);
        System.out.println(Language);

        mainanchor.setVisible(true);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(false);
    }

    @FXML
    public  void Upload_run_zip(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload source file");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String filename = file.getAbsolutePath();

            boolean ret = Compiler.getInstance().compile(filename);
            System.out.println(ret);
        }

    }
    @FXML
    public void show_assignment_results(){
        mainanchor.setVisible(false);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(true);
    }

    @FXML
    public void onProjectSaveButtonClick() throws IOException {
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

       /* @FXML
        public void returnback(ActionEvent e) throws IOException {
            root = FXMLLoader.load(getClass().getResource("316_Project_mainmenu.fxml"));
            stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        private void createConfiguration() throws IOException {
            configurationStatus = "create new configuration";
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfigurationScreen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("New Configuration");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnCloseRequest(windowEvent -> {
                configurations.getItems().clear();
                configurations.getItems().addAll(configurationsList);
                configurations.setValue(configurationsList.get(configurationsList.size() - 1));
            });
            stage.showAndWait();
        }
        @FXML
        private void createConfiguration() throws IOException {
            configurationStatus = "create new configuration";
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfigurationScreen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("New Configuration");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } */




    }
}