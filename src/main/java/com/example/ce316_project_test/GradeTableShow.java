package com.example.ce316_project_test;

public class GradeTableShow {

    private String id;
    private String name;
    private int grade;

    public GradeTableShow(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return this.grade;
    }

}
