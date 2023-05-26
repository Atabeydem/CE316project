package com.example.ce316_project_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private TableView Config_Table;

    @FXML
    private TableColumn Config_column_ID;

    @FXML
    private TableColumn Config_column_name;

    @FXML
    private TableView ProjectMenuTableView;

    @FXML
    private TableColumn ProjectMenuID;

    @FXML
    private TableColumn ProjectMenuTıtle;

    @FXML
    private TableColumn ProjectMenuGO;


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
    private AnchorPane ProjectMenuAnchor;

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
    private ChoiceBox<Integer> project_id_assignment;

    @FXML
    private TableView student_results_tv;

    @FXML
    private TableColumn student_name_tc;

    @FXML
    private TableColumn student_id_tc;

    @FXML
    private TableColumn student_grade_tc;

    @FXML
    void initialize() {

        openmainscreen();
        language_choicebox.getItems().add("python");
        language_choicebox.getItems().add("Java");
        language_choicebox.getItems().add("C");
        language_choicebox.getItems().add("C++");

        ArrayList<Project> projects = DBConnection.getInstance().getAllPConfigObjects();
        for(Project project: projects){
            project_id_assignment.getItems().add(project.getId());
        }
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

            ProgrammingLanguage config = null;
            try {
                config = new ProgrammingLanguage(id ,title, name , needcompiler,compins,Runins);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            DBConnection.getInstance().addConf(config);



        });
    }


    public void openmainscreen(){
        mainanchor.setVisible(true);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(false);
        ProjectMenuAnchor.setVisible(false);
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
    public void OpenProjectMenu() {
        mainanchor.setVisible(false);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(false);
        ProjectMenuAnchor.setVisible(true);

        String path2 = "images/Go.png";
        Image image2 = new Image(getClass().getResource(path2).toExternalForm());

        ObservableList<TableShow> ProjectList = FXCollections.observableArrayList();

        ProjectMenuTıtle.setCellValueFactory(new PropertyValueFactory<TableShow, String>("title"));
        ProjectMenuGO.setCellValueFactory( new PropertyValueFactory<TableShow, ImageView>("image2") );


        try {
            ArrayList<Project> projectConfigs = DBConnection.getInstance().getAllPConfigObjects();
            for (Project project : projectConfigs) {
                ImageView imageView = new ImageView(image2);
                ProjectList.add(new TableShow(project.getId(), project.getTitle(), imageView));
            }
        } catch (Exception e) {
            System.out.println("PROJEYİ ALAMADI DATABASE HATA VERDİ");
        }

        ProjectMenuTableView.setItems(ProjectList);
    }

    public void selectFromProject() throws SQLException, IOException {
        if (ProjectMenuTableView.getSelectionModel().getSelectedCells() == null ||
                ProjectMenuTableView.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        int index = ProjectMenuTableView.getSelectionModel().getSelectedIndex();

        String LectureName = (String) ProjectMenuTıtle.getCellData(index);


        ObservableList<TablePosition> selectedCells = ProjectMenuTableView.getSelectionModel().getSelectedCells();



      /*  if (selectedCells.get(0).getTableColumn().equals(LectureTrashColumn)) {
            //DBConnection.getInstance().deleteTemplate(LectureName);
            System.out.println(LectureName);
            LectureConfig Lecture = DBConnector.getInstance().getLecture(LectureName);
            DBConnector.getInstance().deleteLectureObject(Lecture.getLecture_id());
            openLectureScreen();
        } */
        if (selectedCells.get(0).getTableColumn().equals(ProjectMenuGO)) {
            show_assignment_results();
           /* Project Lecture = DBConnection.getInstance().getLecture(LectureName);
            openProjectScreen(Lecture.getLecture_id());  */
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
    public void Upload_run_zip(){
        int project_id = project_id_assignment.getSelectionModel().getSelectedItem();
        Project project_config = DBConnection.getInstance().getPConfigObject(project_id);
        int pl_config_id = project_config.getConfiguration_id();
        ProgrammingLanguage pl_config = DBConnection.getInstance().getConfigurationObject(pl_config_id);
        DBConnection.getInstance().deleteGradeObject(project_config.getId());
        DBConnection.getInstance().deleteStudents();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open 'projects' folder");
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            File[] zipFiles = directory.listFiles((dir, name) -> name.endsWith(".zip"));
            if(zipFiles != null){
                for(File zipFile : zipFiles){
                    String filename = zipFile.getName().replace(".zip", "");
                    String destinationPath = "src/main/resources/submissions/project-"+String.valueOf(project_config.getId())+"/"+filename;
                    File folder = new File(destinationPath);

                    extractZipFile(zipFile, folder);

                    String folderName = folder.getName();
                    double score = pl_config.executeAndEvaluate(new File(folder.getAbsolutePath()+"/"+filename+"/"+project_config.getMainFileFormat()), project_config.getEvaluations(), true);
                    if(Double.isNaN(score)){
                        score=0;
                    }
                    System.out.println("Score for "+folderName+" is "+score);
                    String[] student_info = folderName.split("_");
                    String student_name = splitCamelCase(student_info[1]);
                    System.out.printf("%s - %f\n", student_name, score);

                    Student student = new Student(student_info[0], student_name);
                    DBConnection.getInstance().addStudent(student);
                    Grade grade = new Grade(-1, project_config.getId(), student.getId(), (int) score);
                    DBConnection.getInstance().addGrade(grade);

                    deleteFolderRecursive(destinationPath);
                }
            }
        }
        open_assignment_results(project_config.getId());
    }

    private String splitCamelCase(String input) {
        Pattern pattern = Pattern.compile("(?<=[a-z])(?=[A-Z])");
        Matcher matcher = pattern.matcher(input);
        String result = matcher.replaceAll(" ");
        return result;
    }

    private void deleteFolderRecursive(String destinationPath){
        try {
            Path folder = Path.of(destinationPath);
            Files.walkFileTree(folder, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Folder removed successfully.");
        } catch (IOException e) {
            System.out.println("Error removing folder: " + e.getMessage());
        }
    }

    private void extractZipFile(File zipFile, File outputDirectory) {
        try {
            ZipFile zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> entries = zip.entries();

            byte[] buffer = new byte[1024];
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(outputDirectory, entry.getName());

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    entryFile.getParentFile().mkdirs();
                    InputStream inputStream = zip.getInputStream(entry);
                    FileOutputStream outputStream = new FileOutputStream(entryFile);
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.close();
                    inputStream.close();
                }
            }

            zip.close();
            System.out.println("Extraction completed successfully.");
        } catch (IOException e) {
            System.out.println("Error extracting zip file: " + e.getMessage());
        }
    }

    @FXML
    public void show_assignment_results(){
        mainanchor.setVisible(false);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(true);
    }

    public void open_assignment_results(int id){
        mainanchor.setVisible(false);
        project_anchor.setVisible(false);
        configuration_anchor.setVisible(false);
        assign_anchor.setVisible(true);

        student_id_tc.setCellValueFactory(new PropertyValueFactory<GradeTableShow, String>("id"));
        student_name_tc.setCellValueFactory(new PropertyValueFactory<GradeTableShow, String>("name"));
        student_grade_tc.setCellValueFactory(new PropertyValueFactory<GradeTableShow, String>("grade"));

        ObservableList<GradeTableShow> student_grade_list = FXCollections
                .observableArrayList();

        ArrayList<Grade> grades = DBConnection.getInstance().getGradesObject(id);
        for (Grade grade : grades) {
            Student student = DBConnection.getInstance().getStudentObject(grade.getStudent_id());
            student_grade_list.add(new GradeTableShow(student.getId(), student.getName() ,grade.getGrade()));
        }

        student_results_tv.setItems(student_grade_list);
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