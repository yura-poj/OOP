package ru.nsu.pozhidaev;

import java.io.IOException;
import java.util.ArrayList;

public class SearchSubstring {
    Utilities utilities;
    ArrayList<Integer> results = new ArrayList<>();

    public SearchSubstring() {
        results = new ArrayList<>();
    }

    public ArrayList<Integer> search(String filename, String text) throws IOException {
        utilities = new Utilities(filename);
        int i = 0;
        char ch = 0;
        ArrayList<Character> buffer = new ArrayList<>();
        for(int j = 0; j < text.length(); j++){
            ch = (char) utilities.newChar();
            buffer.add(ch);
        }
        buffer.toArray();
        while(ch != '\uFFFF'){
            if(compareCharArray(buffer, text)){
                results.add(i);
            }

            buffer.remove(0);
            ch = (char) utilities.newChar();
            buffer.add((char) ch);
            i++;
        }

        return results;
    }

    private boolean compareCharArray(ArrayList<Character> buffer, String text){
        for(int i = 0; i < text.length(); i++){
            if(buffer.get(i) != text.charAt(i)){
                return false;
            }
        }
        return true;
    }

}
