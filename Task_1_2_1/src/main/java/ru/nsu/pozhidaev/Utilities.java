package ru.nsu.pozhidaev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilities {

    private Scanner scanner;

    public Utilities(String fileName) {
        File file;

        file = new File(fileName);

        try{
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String newLine(){
        if(!scanner.hasNextLine()){
            return "";
        }
        return scanner.nextLine().replace(" ", "");
    }
}
