package com.example.ce316_project_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compiler {
    private static Compiler instance = new Compiler();

    public static void main(String [] args){
        Compiler python_compiler = new Compiler();
        String python_file_name= "C:/Users/PC/Desktop/main.py";
        python_compiler.interpret(python_file_name);
    }


    public static Compiler getInstance()
    {
        return instance;
    }

    public boolean interpret(String filename)
    {
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] commands = {"python.exe", filename};
            Process proc = runtime.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String output = "";


            while ((output = stdInput.readLine()) != null) {
                System.out.println(output);
            }


            while ((output = stdError.readLine()) != null ){
                System.out.println(output);
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean compileJava(String filename)
    {
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] commands = {"javac.exe", filename};
            Process proc = runtime.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String output = "";

            /*
            while ((output = stdInput.readLine()) != null) {
                System.out.println(output);
            }
            */

            while ((output = stdError.readLine()) != null ){
                System.out.println(output);
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean compile(String filename)
    {
        int index = filename.lastIndexOf(".");

        if (index == -1)
            return false;

        String ext = filename.substring(index+1);

        switch (ext) {
            case "py":
                return interpret(filename);
            case "java":
                return compileJava(filename);
        }

        return false;
    }
}
