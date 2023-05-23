package com.example.ce316_project_test;

public class ProgrammingLanguage {
    private int id;
    private String name;
    private String title;
    private boolean need_compiler;
    private String compileInsString;
    private String runInsString;

    public ProgrammingLanguage(int id,String title, String name, boolean need_compiler, String compileInsString, String runInsString) {
        this.id = id;
        this.title=title;
        this.name = name;
        this.need_compiler = need_compiler;
        this.compileInsString = compileInsString;
        this.runInsString = runInsString;
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNeed_compiler() {
        return need_compiler;
    }

    public void setNeed_compiler(boolean need_compiler) {
        this.need_compiler = need_compiler;
    }

    public String getCompileInsString() {
        return compileInsString;
    }

    public void setCompileInsString(String compileInsString) {
        this.compileInsString = compileInsString;
    }

    public String getRunInsString() {
        return runInsString;
    }

    public void setRunInsString(String runInsString) {
        this.runInsString = runInsString;
    }
}
