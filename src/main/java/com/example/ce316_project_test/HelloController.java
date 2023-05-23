package com.example.ce316_project_test;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {
    @FXML
    private TableView Config_Table;

    @FXML
    private TableColumn Config_column_ID;

    @FXML
    private TableColumn Config_column_name;

    @FXML
    private Button Add_Language_button;

    @FXML
    private TextField Compiler_Instrcution_TXT;

    @FXML
    private TextField Compiler_TXT;

    @FXML
    private GridPane Config_Grid;

    @FXML
    private TextField Language_ID_TXT;

    @FXML
    private TextField Language_Name_TXT;

    @FXML
    private TextField Language_Title_TXT;

    @FXML
    private TextField Run_Instruction_TXT;
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
    private Button Config_back_mainmenu;

    @FXML
    private Button Add_argument_button;

    @FXML
    private GridPane Project_Grid;

    @FXML
    private GridPane arguman_gridpane;
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

        Add_Language_button.setOnAction(event -> {
            int id;
            String title;
            String name;
            String needcomp;
            String compins;
            String Runins;

            id=Integer.parseInt(Language_ID_TXT.getText());
            title=Language_Title_TXT.getText();
            name=Language_Name_TXT.getText();
            needcomp=Compiler_TXT.getText();
            boolean needcompiler=Boolean.parseBoolean(needcomp);

            compins=Compiler_Instrcution_TXT.getText();
            Runins=Run_Instruction_TXT.getText();

            ProgrammingLanguage config = new ProgrammingLanguage(id ,title, name , needcompiler,compins,Runins);

            DBConnection.getInstance().addConf(config);



        });
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


            Label l1 = new Label("Project ID :");


            Label ProjectTitleLabel = new Label("Project Title : ");


            Label ProjectPL_IDLabel = new Label("Configuration ID: ");

            Label Project_Main_File_Name_FormatLabel = new Label("Main File Name Format : ");

            TextField l2 = new TextField();

            TextField selectedConfig_ID=new TextField();

            TextField selectedProjectTitle = new TextField();


            TextField selectedMain_File_Format=new TextField();

            Project_Grid.add(l1,0,0);

            Project_Grid.add(ProjectTitleLabel,0,1);
            Project_Grid.add(ProjectPL_IDLabel,0,2);
            Project_Grid.add(Project_Main_File_Name_FormatLabel,0,3);

            Project_Grid.add(l2,1,0);
            Project_Grid.add(selectedProjectTitle,1,1);

            Project_Grid.add(selectedConfig_ID,1,2);
            Project_Grid.add(selectedMain_File_Format,1,3);

            ArrayList<TextField> inputs = new ArrayList<>();
            ArrayList<TextField> outputs = new ArrayList<>();

            Add_argument_button.setOnAction(event -> {
                Label label = new Label("Arguments: ");
                Label label2=new Label("Expected Output");
                TextField input_text_field = new TextField();
                TextField output_text_field= new TextField();
                inputs.add(input_text_field);
                outputs.add(output_text_field);
                arguman_gridpane.addRow(arguman_gridpane.getRowCount() + 1, label, input_text_field,label2,output_text_field);
            });

            save_button.setOnAction(event -> {
                int TempP_ID=Integer.parseInt(l2.getText());

                String Temp_PT =selectedProjectTitle.getText();


                String TempM_F_F=selectedMain_File_Format.getText();

                int TempPL_ID=Integer.parseInt(selectedConfig_ID.getText());



                ArrayList<Evaluation> evaluations = new ArrayList<>();
                for(int i=0; i<inputs.size(); i++) {
                    String input_arg = inputs.get(i).getText().trim();
                    String output_arg = outputs.get(i).getText().trim();
                    if(!input_arg.equals("") || !output_arg.equals("")){
                        Evaluation evaluation = new Evaluation(-1,TempP_ID, input_arg, output_arg);
                        evaluations.add(evaluation);
                    }
                }
                Project Project = new Project(TempP_ID, Temp_PT, TempPL_ID, TempM_F_F, evaluations);
                DBConnection.getInstance().addProject(Project);

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void help_action(){

    }

    /*  @FXML
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
  */
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

  /*  @FXML
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
*/
       /* @FXML
        public void returnback(ActionEvent e) throws IOException {
            root = FXMLLoader.load(getClass().getResource("MAIN_MENU.fxml"));
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