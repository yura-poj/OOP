package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GradeTest {
    Grade excellentGradeExam = new Grade(5, GradeType.EXAM);
    Grade worstGradePass = new Grade(2, GradeType.PASS);


    @Test
    void getGrade() {
        assertEquals(5, excellentGradeExam.getGrade());
    }

    @Test
    void isSuccess() {
        assertTrue(excellentGradeExam.isSuccess());
        assertFalse(worstGradePass.isSuccess());
    }

    @Test
    void getType() {
        assertEquals(GradeType.EXAM, excellentGradeExam.getType());
        assertEquals(GradeType.PASS, worstGradePass.getType());
    }
}