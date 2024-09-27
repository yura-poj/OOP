package ru.nsu.pozhidaev;

import java.io.*;
import java.util.Scanner;

public class Utilities {

    private Scanner scanner;
    private BufferedReader bufferedReader;

    public Utilities(String fileName) {

        try {
            bufferedReader = new BufferedReader (new FileReader(fileName));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public int newChar() throws IOException {
        int result = bufferedReader.read();
        if((char) result == '\n' || (char)result == '\r'){
            return newChar();
        }
        return result;
    }
}