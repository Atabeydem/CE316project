package com.example.ce316_project_test;
import java.util.ArrayList;
public class Project {
    private int id;
    private String title;
    private int configuration_id;
    private String MainFileFormat;
    private ArrayList<Evaluation> evaluations;

    public Project(int id, String title, int configuration_id, String mainFileFormat, ArrayList<Evaluation> evaluations) {
        this.id = id;
        this.title = title;
        this.configuration_id = configuration_id;
        this.MainFileFormat = mainFileFormat;
        this.evaluations = evaluations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getConfiguration_id() {
        return configuration_id;
    }

    public void setConfiguration_id(int configuration_id) {
        this.configuration_id = configuration_id;
    }

    public String getMainFileFormat() {
        return MainFileFormat;
    }

    public void setMainFileFormat(String mainFileFormat) {
        MainFileFormat = mainFileFormat;
    }

    public ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(ArrayList<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
