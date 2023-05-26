package com.example.ce316_project_test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammingLanguage{
    private int id;
    private String title;
    private String programming_language;
    private boolean need_compiler;
    private String compileInsString;
    private String runInsString;

    public ProgrammingLanguage(int id, String title, String programming_language, boolean need_compiler, String compileInsString, String runInsString) throws Exception{
        this.id = id;
        this.title = title.trim();
        this.programming_language = programming_language.trim();
        this.need_compiler = need_compiler;
        this.compileInsString = compileInsString;
        this.runInsString = runInsString.trim();

        if(this.need_compiler && this.compileInsString == null){
            throw new Exception("Compiler instruction string can not be null!");
        }
    }

    public ExecuteStatus executeProgram(File file, String args, boolean debug){
        boolean success;
        String output = null;
        try{
            if(this.need_compiler){
                String modifiedCompileString = this.compileInsString.replace("<PARENT_DIRECTORY>", file.getParentFile().getAbsolutePath()).replace("<FILENAME>", file.getName().split("\\.")[0]);
                output = this.runCommand(modifiedCompileString, debug);
                if(debug){
                    System.out.println("Compile output: " + output);
                }
            }
            String modifiedRunString = this.runInsString.replace("<PARENT_DIRECTORY>", file.getParentFile().getAbsolutePath()).replace("<FILENAME>", file.getName().split("\\.")[0]);
            modifiedRunString = modifiedRunString.replace("<ARGS>", args);
            output = this.runCommand(modifiedRunString, debug);
            if(debug){
                System.out.println("Run output: " + output);
            }
            success = true;
        }
        catch (Exception e) {
            success = false;
            output = e.getMessage();
        }

        return new ExecuteStatus(this.id, this.title, output, success);
    }

    private String runCommand(String command, boolean debug) throws Exception {
        if(debug){
            System.out.println("Running command: " + command);
        }
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", command);
        Process process;
        try {
            process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String output = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            InputStream errorStream = process.getErrorStream();
            scanner = new Scanner(errorStream).useDelimiter("\\A");
            String errorOutput = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            int exitCode;
            try {
                exitCode = process.waitFor();
                if (exitCode == 0) {
                    return output;
                } else {
                    throw new Exception("Command (" + command + ") exited with non-zero exit code: " + exitCode);
                }
            } catch (InterruptedException e) {
                throw new Exception("Command (" + command + ") interrupted: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new Exception("Error running command (" + command + "): " + e.getMessage());
        }
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getProgramming_language(){
        return this.programming_language;
    }

    public double executeAndEvaluate(File file, ArrayList<Evaluation> evaluations, boolean debug) {
        int totalQuestions = evaluations.size();
        int correctAnswers = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Evaluation evaluation = evaluations.get(i);
            String input = evaluation.getPinput();
            String expectedOutput = evaluation.getPoutput();

            ExecuteStatus output = executeProgram(file, input, debug);
            String actualOutput = output.getOutput();
            boolean success = actualOutput.trim().equals(expectedOutput.trim());

            if(debug){
                System.out.println("Input: " + input);
                System.out.println("Expected Output: " + expectedOutput);
                System.out.println("Actual Output: " + actualOutput);
                System.out.println("Success: " + success);
            }

            if (success) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / totalQuestions * 100;

        if(debug){
            System.out.println("Correct Answers: " + correctAnswers);
            System.out.println("Total Questions: " + totalQuestions);
            System.out.println("Overall Score: " + score);
        }

        return score;
    }

    public boolean isNeed_compiler() {
        return need_compiler;
    }

    public String getCompileInsString() {
        return compileInsString;
    }

    public String getRunInsString() {
        return runInsString;
    }
}
