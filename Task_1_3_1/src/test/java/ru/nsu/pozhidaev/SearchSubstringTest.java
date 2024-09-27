package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchSubstringTest {
    SearchSubstring searchSubstring;
    @BeforeEach
    void setUp() {
        searchSubstring = new SearchSubstring();
    }
    @Test
    void search() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(10);

        ArrayList<Integer> list1 =  searchSubstring.search("./src/test/java/ru/nsu/pozhidaev/test.txt", "aboba");
        assertEquals(list, list1);

    }
}