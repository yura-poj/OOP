package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void getRecordBook() {
        assertEquals(RecordBook.class, student.getRecordBook().getClass());
    }
}