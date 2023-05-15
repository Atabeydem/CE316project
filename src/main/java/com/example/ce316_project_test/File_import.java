package com.example.ce316_project_test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class File_import {
    public static void main(String[] args) {
        String kaynakDosya = "C:/kaynak_dizin/kaynak_dosya.txt";
        String hedefDosya = "C:/hedef_dizin/hedef_dosya.txt";

        try {
            Path kaynak = Path.of(kaynakDosya);
            Path hedef = Path.of(hedefDosya);

            // Dosyayı kopyala
            Files.copy(kaynak, hedef, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Dosya kopyalandı.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
