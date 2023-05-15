package com.example.ce316_project_test.db;

public class Project {
    private int id;
    private String name;
    private String description;
    private int pl_id;
    private int lecture_id;

    public Project() {
    }

    public Project(int id, String name, String description, int pl_id, int lecture_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pl_id = pl_id;
        this.lecture_id = lecture_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPl_id() {
        return pl_id;
    }

    public void setPl_id(int pl_id) {
        this.pl_id = pl_id;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }
}
