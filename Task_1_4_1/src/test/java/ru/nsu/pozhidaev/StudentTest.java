package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;
    @BeforeEach
    void setUp() {
        student = new Student("Lexa");
    }

    @Test
    void getName() {
        assertEquals("Lexa", student.getName());
    }
}