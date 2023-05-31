package com.example.ce316_project_test;

class ExecuteStatus{
    private int ID;
    private String Name;
    private String Output;
    private boolean Success;

    ExecuteStatus(int id, String name, String output, boolean success){
        this.ID = id;
        this.Name = name;
        this.Output = output;
        this.Success = success;
    }

    public int getID(){
        return this.ID;
    }

    public String getName(){
        return this.Name;
    }

    public String getOutput(){
        return this.Output;
    }

    public boolean getSuccess(){
        return this.Success;
    }
}
