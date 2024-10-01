package ru.nsu.pozhidaev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class SearchSubstring {
    private ArrayList<Integer> results;

    public SearchSubstring() {
        results = new ArrayList<>();
    }

    public ArrayList<Integer> search(Reader reader, String text) throws IOException {
        int startSubstringIndex = 0;
        char currentChar = 0;
        ArrayList<Character> buffer = new ArrayList<>();
        for(int j = 0; j < text.length(); j++){
            currentChar = newChar(reader);
            buffer.add(currentChar);
        }
        buffer.toArray();
        while(currentChar != '\uFFFF'){
            if(compareCharArray(buffer, text)){
                results.add(startSubstringIndex);
            }

            buffer.remove(0);
            currentChar = newChar(reader);
            buffer.add(currentChar);
            startSubstringIndex++;
        }

        return results;
    }

    public ArrayList<Integer> search(String reader, String text) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(reader));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return search(bufferedReader, text);
    }

    private boolean compareCharArray(ArrayList<Character> buffer, String text){
        for(int i = 0; i < text.length(); i++){
            if(buffer.get(i) != text.charAt(i)){
                return false;
            }
        }
        return true;
    }

    private Character newChar(Reader reader) throws IOException {
        char result = '0';
        while(true){
            result = (char) reader.read();
            if(result != '\n' && result != '\r'){
                return result;
            }
        }

    }
}
