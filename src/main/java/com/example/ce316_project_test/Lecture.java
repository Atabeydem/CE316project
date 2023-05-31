
package com.example.ce316_project_test;

public class Lecture {
    private int id;
    private String name;
    private String lecturer;

    public Lecture()
    {

    }

    public Lecture(int id, String name, String lecturer) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
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

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
