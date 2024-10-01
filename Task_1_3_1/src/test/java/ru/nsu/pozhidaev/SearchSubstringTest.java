package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchSubstringTest {
    SearchSubstring searchSubstring;

    @BeforeEach
    void setUp() {
        searchSubstring = new SearchSubstring();
    }

    @Test
    void searchFileName() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(10);

        ArrayList<Integer> list1 = searchSubstring.search("./src/test/resources/test.txt", "aboba");
        assertEquals(list, list1);
    }

    @Test
    void searchFileReader() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(10);
        FileReader fileReader = new FileReader("./src/test/resources/test.txt");
        ArrayList<Integer> list1 = searchSubstring.search(fileReader, "aboba");
        assertEquals(list, list1);
    }

    @Test
    void searchStrangeText() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(22);

        ArrayList<Integer> list1 = searchSubstring.search(
                "./src/test/resources/test2.txt", "世界☀★♛");
        assertEquals(list, list1);
    }

    @Test
    void generateBigTestString() throws IOException {
        File file = new File("./src/test/resources/test1.txt");
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.append("abbaboba");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            bufferedWriter.append((char) ('c' + (i % 24)));
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        ArrayList<Integer> list1 = searchSubstring.search(
                "./src/test/resources/test1.txt", "aboba");
        assertEquals(list, list1);
        file.delete();
    }

    @Test
    void generateBigTestFile() throws IOException {
        File file = new File("./src/test/resources/test1.txt");
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.append("abbaboba");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            bufferedWriter.append((char) ('c' + (i % 24)));
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        BufferedReader fileReader =
                new BufferedReader(new FileReader("./src/test/resources/test1.txt"));

        ArrayList<Integer> list1 = searchSubstring.search(fileReader, "aboba");
        assertEquals(list, list1);
        file.delete();
    }
}
