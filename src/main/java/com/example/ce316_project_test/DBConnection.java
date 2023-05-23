package com.example.ce316_project_test;

import java.io.File;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private final String fileName;
    private Connection conn;
    private PreparedStatement insertSQL;
    private PreparedStatement selectSQL;

    private PreparedStatement insertConfiguration,insertEvaluation,insertProject;
     DBConnection() {
        fileName = "ce316.db";
        File file = new File(fileName);
        boolean firstRun = !file.exists();
        conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            if (firstRun) {
                Statement stat = conn.createStatement();
                // Enables foreign keys feature for sqlite
                stat.executeUpdate("PRAGMA foreign_keys = ON;");
                // Configurations Table
                stat.executeUpdate("CREATE TABLE configurations(" +
                        "configuration_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "configuration_title VARCHAR(50) NOT NULL," +
                        "programming_language VARCHAR(10)," +
                        "configuration_needcompiler INTEGER,"+
                        "configuration_compileins TEXT,"+
                        "configuration_runins TEXT);");

                // Projects Table
                stat.executeUpdate("CREATE TABLE projects(" +
                        "project_id INTEGER ," +
                        "project_title VARCHAR(50) NOT NULL," +
                        "File_Format TEXT,"+
                        "configuration_id INTEGER NOT NULL);");
                // Submissions Table
                stat.executeUpdate("CREATE TABLE submissions(" +
                        "submission_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "student_id VARCHAR(20) NOT NULL," +
                        "status VARCHAR(10)," +
                        "error VARCHAR(50)," +
                        "submission_output TEXT," +
                        "project_id INTEGER NOT NULL," +
                        "FOREIGN KEY (project_id) REFERENCES projects(project_id));");

                stat.executeUpdate("CREATE TABLE IF NOT EXISTS Evaluation_Table (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "PROJECT_ID INTEGER," +
                        "P_INPUT TEXT," +
                        "P_OUTPUT TEXT)");

                insertConfiguration = conn.prepareStatement("INSERT INTO configurations (configuration_id, configuration_title, programming_language, configuration_needcompiler, configuration_compileins, configuration_runins) VALUES (?,?,?,?,?,?)");
                insertEvaluation = conn.prepareStatement("INSERT INTO Evaluation_Table (PROJECT_ID, P_INPUT, P_OUTPUT) VALUES (?,?,?)");
                insertProject = conn.prepareStatement("INSERT INTO projects (project_id, project_title,File_Format,configuration_id) VALUES (?,?,?,?)");
               // insertSubmissions
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }

       // fetchProgrammingLanguages();
    }
    public void addConf(ProgrammingLanguage language) {
        try {
            int id = language.getId();
            String title = language.getTitle();
            String name = language.getName();

            boolean need_compiler = language.isNeed_compiler();
            String compileInsString = language.getCompileInsString();
            String runInsString = language.getRunInsString();

            int need_compilerint;
            if (need_compiler == true) {
                need_compilerint = 1;
            } else {
                need_compilerint = 0;
            }
            insertConfiguration.setInt(1, id);
            insertConfiguration.setString(2, title);
            insertConfiguration.setString(3,name);
            insertConfiguration.setInt(4, need_compilerint);
            insertConfiguration.setString(5, compileInsString);
            insertConfiguration.setString(6, runInsString);
            insertConfiguration.execute();

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void addProject(Project project) {
        try {

            String title=project.getTitle();
            int project_id= project.getId();
            int configuration_id=project.getConfiguration_id();
            String mainFileFormat=project.getMainFileFormat();
            insertProject.setInt(1,project_id);
            insertProject.setString(2, title);
            insertProject.setString(3,mainFileFormat);
            insertProject.setInt(4,configuration_id);
            insertProject.execute();

            ArrayList<Evaluation> evaluations = project.getEvaluations();
            for(Evaluation evaluation : evaluations){
                addEvaluation(evaluation);
            }

        } catch (Exception e) {
            System.err.println("sıkıntı oluştu" + e);
        }
    }

    public void addEvaluation(Evaluation evo){
        try {
            int prjojectid = evo.getProject_ID();
            String pinput = evo.getPinput();
            String pout = evo.getPoutput();

            insertEvaluation.setInt(1,prjojectid);
            insertEvaluation.setString(2,pinput);
            insertEvaluation.setString(3,pout);
            insertEvaluation.execute();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public boolean insertLecture(Lecture lecture)
    {
        try {
            String query = "INSERT INTO Lecture(name, lecturer) VALUES (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, lecture.getName());
            stmt.setString(2, lecture.getLecturer());
            stmt.executeUpdate();

            stmt.close();

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public List<Lecture> fetchLectures() {
        List<Lecture> lectures = new ArrayList<>();
        String query = "SELECT * FROM Lecture";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String lecturer = rs.getString("lecturer");
                Lecture lecture = new Lecture(id, name, lecturer);
                lectures.add(lecture);
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return lectures;
    }

   /* public List<Project> fetchProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int pl_id = rs.getInt("pl_id");
                int lecture_id = rs.getInt("lecture_id");
                Project project = new Project(id, name, description, pl_id, lecture_id);
                projects.add(project);
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return projects;
    }
*/
 /*   public List<ProgrammingLanguage> fetchProgrammingLanguages() {
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        String query = "SELECT * FROM ProgrammingLanguage";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ProgrammingLanguage pl = new ProgrammingLanguage(id, name);
                programmingLanguages.add(pl);
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return programmingLanguages;
    } */

    /*

    public ArrayList<Configuration> getAllConfigurations() throws SQLException {
        ArrayList<Configuration> configurations = new ArrayList<>();
        String query = "SELECT " +
                "configuration_title, " +
                "programming_language, " +
                "lecturer_code_path, " +
                "lib, " +
                "args," +
                "configuration_output " +
                "FROM configurations";

        selectSQL = conn.prepareStatement(query);
        ResultSet rs = selectSQL.executeQuery();

        while (rs.next()) {
            String title = rs.getString("configuration_title");
            String language = rs.getString("programming_language");
            String codePath = rs.getString("lecturer_code_path");
            String lib = rs.getString("lib");
            String args = rs.getString("args");
            String output = rs.getString("configuration_output");
            Configuration configuration = new Configuration(title, language, lib, args, codePath);
            configuration.setOutput(output);
            configurations.add(configuration);
        }

        rs.close();
        selectSQL.close();

        return configurations;
    }

    public ArrayList<Project> getAllProjects() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        String query = "SELECT project_title, configuration_id FROM projects";

        selectSQL = conn.prepareStatement(query);
        ResultSet rs = selectSQL.executeQuery();

        while (rs.next()) {
            String title = rs.getString("project_title");
            int configurationID = rs.getInt("configuration_id");
            Configuration c = getConfigurationByID(configurationID);
            Project p = new Project(title, c);
            projects.add(p);
        }

        rs.close();
        selectSQL.close();

        return projects;
    }

    public ArrayList<Submission> getAllSubmissions() throws SQLException {
        ArrayList<Submission> submissions = new ArrayList<>();
        String query = "SELECT student_id, " +
                "project_id, " +
                "status, " +
                "submission_output, " +
                "error " +
                "FROM submissions";

        selectSQL = conn.prepareStatement(query);
        ResultSet rs = selectSQL.executeQuery();

        while (rs.next()) {
            String studentId = rs.getString("student_id");
            int projectId = rs.getInt("project_id");
            Project project = getProjectByID(projectId);

            String status = rs.getString("status");
            String error = rs.getString("error");
            String output = rs.getString("submission_output");
            Submission submission = new Submission(project,studentId,status,error,output);
            submissions.add(submission);
        }

        rs.close();
        selectSQL.close();

        return submissions;
    }

    private Project getProjectByID(int id) throws SQLException {
        Project project = null;
        String query = "SELECT project_title, " +
                "configuration_id " +
                "FROM projects " +
                "WHERE project_id = ?";

        selectSQL = conn.prepareStatement(query);
        selectSQL.setInt(1, id);

        ResultSet rs = selectSQL.executeQuery();

        if (rs.next()) {
            String title = rs.getString("project_title");
            int configurationID = rs.getInt("configuration_id");
            Configuration c = getConfigurationByID(configurationID);
            project = new Project(title, c);
        }

        rs.close();
        selectSQL.close();

        return project;
    }

    private Configuration getConfigurationByID(int id) throws SQLException {
        Configuration configuration = null;
        String query = "SELECT " +
                "configuration_id, " +
                "configuration_title, " +
                "programming_language, " +
                "lecturer_code_path, " +
                "lib, " +
                "args, " +
                "configuration_output " +
                "FROM configurations " +
                "WHERE configuration_id = ?";

        selectSQL = conn.prepareStatement(query);
        selectSQL.setInt(1, id);

        ResultSet rs = selectSQL.executeQuery();

        if (rs.next()) {
            String title = rs.getString("configuration_title");
            String language = rs.getString("programming_language");
            String codePath = rs.getString("lecturer_code_path");
            String lib = rs.getString("lib");
            String args = rs.getString("args");
            String output = rs.getString("configuration_output");

            configuration = new Configuration(title, language, lib, args, codePath);
            configuration.setOutput(output);

        }

        selectSQL.close();
        rs.close();

        return configuration;
    }

    // It returns a configuration_id from configurations table using configuration_title
    private int getConfigurationID(Configuration configuration) throws SQLException {
        String query = "SELECT configuration_id " +
                "FROM configurations " +
                "WHERE configuration_title = ?";

        selectSQL = conn.prepareStatement(query);
        selectSQL.setString(1, configuration.getTitle());

        ResultSet rs = selectSQL.executeQuery();
        int configurationID = rs.getInt("configuration_id");

        rs.close();
        selectSQL.close();

        System.out.println(configurationID);

        return configurationID;
    }

    private int getProjectId(String projectTitle) throws SQLException {
        String query = "SELECT project_id FROM projects WHERE project_title = ?";

        selectSQL = conn.prepareStatement(query);
        selectSQL.setString(1, projectTitle);

        ResultSet rs = selectSQL.executeQuery();
        int projectID = rs.getInt("project_id");

        rs.close();
        selectSQL.close();

        return projectID;
    }

    public void addProject(Project project) throws SQLException {
        int cfgID = getConfigurationID(project.getConfiguration());

        String query = "INSERT INTO projects(project_title, configuration_id) VALUES (?, ?);";
        insertSQL = conn.prepareStatement(query);
        insertSQL.setString(1, project.getTitle());
        insertSQL.setInt(2, cfgID);
        insertSQL.executeUpdate();

        insertSQL.close();

        System.out.println("Project entity added successfully!");

    }

    public void addConfiguration(Configuration c) throws SQLException {
        String query = "INSERT INTO configurations(" +
                "configuration_title, " +
                "programming_language, " +
                "lecturer_code_path, " +
                "lib, " +
                "args," +
                "configuration_output) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        insertSQL = conn.prepareStatement(query);

        insertSQL.setString(1, c.getTitle());
        insertSQL.setString(2, c.getLang());
        insertSQL.setString(3, c.getDirectory());
        insertSQL.setString(4, c.getLib());
        insertSQL.setString(5, c.getArgs());
        insertSQL.setString(6, c.getOutput());
        insertSQL.executeUpdate();

        insertSQL.close();

        System.out.println("Configuration entity added successfully!");

    }

    public void addSubmission(Submission s) throws SQLException {
        String query =  "INSERT INTO submissions(" +
                "student_id, " +
                "status, " +
                "error, " +
                "submission_output, " +
                "project_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        insertSQL = conn.prepareStatement(query);

        insertSQL.setString(1, s.getStudentID());
        insertSQL.setString(2, s.getStatus());
        insertSQL.setString(3,s.getError());
        insertSQL.setString(4, s.getOutput());
        int id = getProjectId(s.getProject().getTitle());
        insertSQL.setInt(5, id);
        insertSQL.executeUpdate();

        insertSQL.close();
    }

    */
    private static DBConnection instance = null;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}