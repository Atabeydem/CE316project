package com.example.ce316_project_test;

public class Evaluation {
    private int ID;
    private int Project_ID;
    private String pinput;
    private String poutput;

    public Evaluation(int id, int project_id, String pinput, String poutput) {
        this.ID = id;
        this.Project_ID = project_id;
        this.pinput = pinput;
        this.poutput = poutput;
    }

    public Evaluation(int project_id, String pinput, String poutput) {
        this.Project_ID = project_id;
        this.pinput = pinput;
        this.poutput = poutput;
    }

    public int getID() {
        return ID;
    }

    public int getProject_ID() {
        return Project_ID;
    }

    public String getPinput() {
        return pinput;
    }

    public String getPoutput() {
        return poutput;
    }
}
