package com.example.ce316_project_test.db;

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

    private DBConnection() {
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
                        "lecturer_code_path VARCHAR(100)," +
                        "lib VARCHAR(100)," +
                        "args VARCHAR(100)," +
                        "configuration_output TEXT," +
                        "created_at DATE DEFAULT CURRENT_TIMESTAMP);");
                // Projects Table
                stat.executeUpdate("CREATE TABLE projects(" +
                        "project_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "project_title VARCHAR(50) NOT NULL," +
                        "created_at DATE DEFAULT CURRENT_TIMESTAMP," +
                        "configuration_id INTEGER NOT NULL," +
                        "FOREIGN KEY (configuration_id) REFERENCES configurations(configuration_id));");
                // Submissions Table
                stat.executeUpdate("CREATE TABLE submissions(" +
                        "submission_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "student_id VARCHAR(20) NOT NULL," +
                        "status VARCHAR(10)," +
                        "error VARCHAR(50)," +
                        "submission_output TEXT," +
                        "created_at DATE DEFAULT CURRENT_TIMESTAMP," +
                        "project_id INTEGER NOT NULL," +
                        "FOREIGN KEY (project_id) REFERENCES projects(project_id));");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }

        fetchProgrammingLanguages();
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

    public boolean insertProject(Project project)
    {
        try {
            String query = "INSERT INTO Project(name, description, pl_id, lecture_id) VALUES (?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getPl_id());
            stmt.setInt(4, project.getLecture_id());
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

    public List<Project> fetchProjects() {
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

    public List<ProgrammingLanguage> fetchProgrammingLanguages() {
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
    }

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