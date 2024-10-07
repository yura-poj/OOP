package ru.nsu.pozhidaev;

/**
 * grade type.
 * the most important is Exam and Diffpass, they role of getting higher grant or red diploma.
 * pass is just true or false without number.
 * also without excellent qualification you won't get red diploma.
 */
public enum GradeType {
    EXAM,
    TASK,
    CONTROL,
    COLLOQUIUM,
    DIFFPASS,
    PASS,
    QUALIFICATION,
    PRACTICE
}
