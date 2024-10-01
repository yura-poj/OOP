package ru.nsu.pozhidaev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/** search substring from class Reader or string. */
public class SearchSubstring {
    private ArrayList<Integer> results;

    /** constructor. */
    public SearchSubstring() {
        results = new ArrayList<>();
    }

    /**
     * search substring. create buffer with length of param text, and compare it with substring of
     * size of param text in file. after each iteration remove first element in buffer and add next.
     *
     * @param reader subclass of Reader which will be called in function to get chars.
     * @param text you want to find in file.
     * @return array list with indexes of first letters of substring.
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     */
    public ArrayList<Integer> search(Reader reader, String text) throws IOException {
        int startSubstringIndex = 0;
        char currentChar = 0;
        ArrayList<Character> buffer = new ArrayList<>();
        for (int j = 0; j < text.length(); j++) {
            currentChar = newChar(reader);
            buffer.add(currentChar);
        }
        buffer.toArray();
        while (currentChar != '\uFFFF') {
            if (compareCharArray(buffer, text)) {
                results.add(startSubstringIndex);
            }

            buffer.remove(0);
            currentChar = newChar(reader);
            buffer.add(currentChar);
            startSubstringIndex++;
        }

        return results;
    }

    /**
     * overloaded method that open file reader by string and call another search.
     *
     * @param reader string of name of file which will be opened.
     * @param text substring that should be found.
     * @return array list with indexes of first letters of substring.
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     */
    public ArrayList<Integer> search(String reader, String text) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(reader));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return search(bufferedReader, text);
    }

    /**
     * compare array list of chars and string.
     *
     * @param buffer array list of chars.
     * @param text string to compare.
     * @return true if array equal string, and false in other case.
     */
    private boolean compareCharArray(ArrayList<Character> buffer, String text) {
        for (int i = 0; i < text.length(); i++) {
            if (buffer.get(i) != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * get new char from Reader and remove extra values like '\n' '\r' etc.
     *
     * @param reader from which will getting chars.
     * @return new char.
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     */
    private Character newChar(Reader reader) throws IOException {
        char result = '0';
        while (true) {
            result = (char) reader.read();
            if (result != '\n' && result != '\r') {
                return result;
            }
        }
    }
}
