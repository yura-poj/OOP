package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void SampleTest(){
        assertEquals(3, Main.sum(1,2));
    }
}