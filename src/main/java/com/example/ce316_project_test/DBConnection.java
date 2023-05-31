package com.example.ce316_project_test;

import java.io.File;
import java.lang.module.Configuration;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static DBConnection instance = null;
    private final String fileName;
    private Connection conn;


    private PreparedStatement insertConfiguration,insertEvaluation,insertProject, getAllProjectIds,getProjectConfig,getEvaluations,getConfiguration,
            insertGrade, insertStudent, deleteGrade, getStudent, getAllGrades, deleteStudent;
     DBConnection() {
        fileName = "ce316.db";
        File file = new File(fileName);
        boolean firstRun = !file.exists();


        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            if (firstRun) {
                Statement stat = conn.createStatement();
                // Enables foreign keys feature for sqlite
                // Configurations Table
                stat.executeUpdate("CREATE TABLE IF NOT EXISTS configurations(" +
                        "configuration_id INTEGER ," +
                        "configuration_title VARCHAR(50) ," +
                        "programming_language VARCHAR(10)," +
                        "configuration_needcompiler INTEGER,"+
                        "configuration_compileins TEXT,"+
                        "configuration_runins TEXT);");

                // Projects Table
                stat.executeUpdate("CREATE TABLE IF NOT EXISTS projects(" +
                        "project_id INTEGER ," +
                        "project_title VARCHAR(50)," +
                        "File_Format TEXT,"+
                        "configuration_id INTEGER );");
                // Submissions Table
                stat.executeUpdate("CREATE TABLE IF NOT EXISTS submissions(" +
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

                stat.executeUpdate("CREATE TABLE IF NOT EXISTS Grades (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "PROJECT_ID INTEGER," +
                        "STUDENT_ID TEXT," +
                        "GRADE INTEGER)");

                stat.executeUpdate("CREATE TABLE IF NOT EXISTS Students (" +
                        "STUDENT_ID TEXT PRIMARY KEY," +
                        "STUDENT_NAME TEXT)");
            }

            insertGrade = conn.prepareStatement("INSERT INTO Grades(PROJECT_ID, STUDENT_ID, GRADE) VALUES (?,?,?)");
            insertConfiguration = conn.prepareStatement("INSERT INTO configurations (configuration_id, configuration_title, programming_language, configuration_needcompiler, configuration_compileins, configuration_runins) VALUES (?,?,?,?,?,?)");
            insertEvaluation = conn.prepareStatement("INSERT INTO Evaluation_Table (PROJECT_ID, P_INPUT, P_OUTPUT) VALUES (?,?,?)");
            insertProject = conn.prepareStatement("INSERT INTO projects (project_id, project_title,File_Format,configuration_id) VALUES (?,?,?,?)");
            insertStudent = conn.prepareStatement("INSERT INTO Students (STUDENT_ID, STUDENT_NAME) VALUES (?,?)");
            getAllProjectIds = conn.prepareStatement("SELECT project_id FROM projects ");
            getProjectConfig = conn.prepareStatement("SELECT * FROM projects WHERE project_id = ?");
            getConfiguration = conn.prepareStatement("SELECT * FROM configurations WHERE configuration_id = ?");
            getEvaluations = conn.prepareStatement("SELECT * FROM Evaluation_Table WHERE project_id = ?");
            deleteGrade = conn.prepareStatement("DELETE FROM Grades WHERE PROJECT_ID = ?");
            deleteStudent = conn.prepareStatement("DELETE FROM Students");
            getStudent = conn.prepareStatement("SELECT * FROM Students WHERE STUDENT_ID = ?");
            getAllGrades = conn.prepareStatement("SELECT * FROM Grades WHERE PROJECT_ID = ?");


        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }

       // fetchProgrammingLanguages();
    }


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public ArrayList<Grade> getGradesObject(int projectId) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            getAllGrades.setInt(1, projectId);
            getAllGrades.execute();
            ResultSet rs = getAllGrades.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String studentId = rs.getString(3);
                int grade = rs.getInt(4);
                Grade gradeObject = new Grade(id, projectId, studentId, grade);
                grades.add(gradeObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return grades;
    }

    public Student getStudentObject(String student_id){
        try {
            getStudent.setString(1, student_id);
            getStudent.execute();
            ResultSet rs = getStudent.executeQuery();
            rs.next();

            String name = rs.getString(2);
            Student student = new Student(student_id, name);
            return student;

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public void deleteGradeObject(int project_id){
        try {
            deleteGrade.setInt(1, project_id);
            deleteGrade.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteStudents(){
        try {
            deleteStudent.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addConf(ProgrammingLanguage language) {
        try {
            int id = language.getId();
            String title = language.getTitle();
            String name = language.getProgramming_language();

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

    public ArrayList<Project> getAllPConfigObjects() {
        ArrayList<Project> configList = new ArrayList<>();
        try {
            ResultSet rs = getAllProjectIds.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            try {
                while (rs.next()) {
                    int i = 1;
                    while (i <= columnCount) {
                        int id = rs.getInt(i++);
                        Project config = getPConfigObject(id);
                        configList.add(config);
                    }
                }
            } catch (SQLException e) {
                System.out.println("burada"+e);
            }
            return configList;

        } catch (Exception e) {
            System.out.println("..........."+e);
        }

        return configList;
    }
    public Project getPConfigObject(int id) {
        try {
            getProjectConfig.setInt(1, id);
            getProjectConfig.execute();
            ResultSet rs = getProjectConfig.executeQuery();
            rs.next();

            String title = rs.getString(2);
            String mainFileFormat = rs.getString(3);
            int PlId = rs.getInt(4);

            ArrayList<Evaluation> evaluations = getEvaluationsObject(id);
            Project config = new Project(id, title, PlId, mainFileFormat, evaluations);
            return config;

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public ProgrammingLanguage getConfigurationObject(int id) {
        try {
            getConfiguration.setInt(1, id);
            getConfiguration.execute();
            ResultSet rs = getConfiguration.executeQuery();
            rs.next();

            String title = rs.getString(2);
            String programming_language = rs.getString(3);
            int need_compiler = rs.getInt(4);
            boolean need_compiler_bool = false;
            if(need_compiler == 1){
                need_compiler_bool = true;
            }
            String compile_ins = rs.getString(5);
            String run_ins = rs.getString(6);

            ProgrammingLanguage configuration = new ProgrammingLanguage(id, title, programming_language, need_compiler_bool, compile_ins, run_ins);

            return configuration;

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public ArrayList<Evaluation> getEvaluationsObject(int projectId) {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        try {
            getEvaluations.setInt(1, projectId);
            getEvaluations.execute();
            ResultSet rs = getEvaluations.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String input = rs.getString(3);
                String output = rs.getString(4);
                Evaluation evaluation = new Evaluation(id, projectId, input, output);
                evaluations.add(evaluation);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return evaluations;
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

    public void addStudent(Student st){
        try {
            String id = st.getId();
            String name = st.getName();

            insertStudent.setString(1,id);
            insertStudent.setString(2,name);
            insertStudent.execute();

        }catch (Exception e){
            System.out.println(e);
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

    public void addGrade(Grade grade){
        try {
            int project_id = grade.getProject_id();
            String studentid = grade.getStudent_id();
            int gradeint = grade.getGrade();

            insertGrade.setInt(1,project_id);
            insertGrade.setString(2,studentid);
            insertGrade.setInt(3,gradeint);
            insertGrade.execute();
        }catch (Exception e){
            System.out.println(e);
        }

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

}