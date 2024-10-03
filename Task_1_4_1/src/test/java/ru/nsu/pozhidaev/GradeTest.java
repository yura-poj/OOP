package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {
    Grade grade;
    Grade grade1;
    @BeforeEach
    void setUp() {
        GradeType type = GradeType.EXAM;
        grade = new Grade(5, type);
        GradeType type1 = GradeType.PASS;
        grade1 = new Grade(2, type1);
    }

    @Test
    void getGrade() {
        assertEquals(5, grade.getGrade());
    }

    @Test
    void isSuccess() {
        assertTrue(grade.isSuccess());
        assertFalse(grade1.isSuccess());
    }

    @Test
    void getType() {
        assertEquals(GradeType.EXAM, grade.getType());
        assertEquals(GradeType.PASS, grade1.getType());
    }
}