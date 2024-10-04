package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubjectTest {
    Subject subject;
    ArrayList<Grade> grades;

    @BeforeEach
    void setUp() throws Subject.NoSuchSemesterYet {
        grades = new ArrayList<>();
        grades.add(new Grade(5, GradeType.EXAM));
        grades.add(new Grade(2, GradeType.PASS));
        subject = new Subject("Prolog");
        subject.setGrade(5, 1, GradeType.EXAM);
        subject.setGrade(2, 1, GradeType.PASS);
        subject.nextSemester();
        subject.setGrade(4, 2, GradeType.EXAM);
    }

    @Test
    void getName() {
        assertEquals("Prolog", subject.getName());
    }

    @Test
    void changeName() {
        subject.changeName("Operating System");
        assertEquals("Operating System", subject.getName());
    }

    @Test
    void setGrade() throws Subject.NoSuchSemesterYet {
        subject.setGrade(3, 1, GradeType.EXAM);
        grades.add(new Grade(3, GradeType.EXAM));
        assertEquals(grades, subject.getGrades(1));
    }

    @Test
    void testNextSemester() throws Subject.NoSuchSemesterYet {
        assertThrows(Subject.NoSuchSemesterYet.class, () -> subject.getGrades(3));
        subject.nextSemester();
        assertDoesNotThrow(() -> subject.getGrades(3));
    }
}