package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordBookTest {
    RecordBook recordBook;

    @BeforeEach
    void setUp() throws Subject.NoSuchSemesterYet, RecordBook.NoSuchSubjectYet {
        recordBook = new RecordBook("Lexa");
        recordBook.addSubject("Prolog");
        recordBook.addGrade("Prolog", 5, 1, GradeType.EXAM);
        recordBook.addGrade("Prolog", 4, 1, GradeType.CONTROL);
        recordBook.addGrade("Prolog", 3, 1, GradeType.DIFFPASS);
        recordBook.addPass("Prolog", true, 1);
        recordBook.nextSemester();
    }

    @Test
    void nextSemester() {
        recordBook.nextSemester();
        assertEquals(3, recordBook.getCurrentSemester());
    }

    @Test
    void getCurrentSemester() {
        assertEquals(2, recordBook.getCurrentSemester());
    }

    @Test
    void gradePointAverage() {
        assertEquals(4.0, recordBook.gradePointAverage());
    }

    @Test
    void chanceToStudyFree() throws Subject.NoSuchSemesterYet, RecordBook.NoSuchSubjectYet {
        assertTrue(recordBook.chanceToStudyFree());
        recordBook.addGrade("Prolog", 3, 2, GradeType.EXAM);
        assertFalse(recordBook.chanceToStudyFree());
    }

    @Test
    void chanceToGetRedDiplomaTrue() throws Subject.NoSuchSemesterYet, RecordBook.NoSuchSubjectYet {
        recordBook = new RecordBook("Lexa");
        recordBook.addSubject("Prolog");
        recordBook.addGrade("Prolog", 5, 1, GradeType.EXAM);
        recordBook.addGrade("Prolog", 5, 1, GradeType.DIFFPASS);
        recordBook.addGrade("Prolog", 5, 1, GradeType.QUALIFICATION);
        assertTrue(recordBook.chanceToGetRedDiploma());
    }

    @Test
    void chanceToGetRedDiplomaFalse() throws Subject.NoSuchSemesterYet, RecordBook.NoSuchSubjectYet {
        recordBook.addGrade("Prolog", 5, 1, GradeType.QUALIFICATION);
        assertFalse(recordBook.chanceToGetRedDiploma());
    }

    @Test
    void chanceToGetHigherGrants() throws Subject.NoSuchSemesterYet, RecordBook.NoSuchSubjectYet {
        recordBook.addGrade("Prolog", 5, 2, GradeType.EXAM);
        assertTrue(recordBook.chanceToGetHigherGrants());
        recordBook.addGrade("Prolog", 4, 2, GradeType.DIFFPASS);
        assertFalse(recordBook.chanceToGetHigherGrants());
    }
}