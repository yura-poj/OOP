package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * subject of studying, with their own grades.
 */
public class Subject {
    private String name;
    private ArrayList<ArrayList<Grade>> grades;
    private int semester;

    /**
     * constructor.
     *
     * @param name of subject.
     */
    public Subject(String name) {
        this.name = name;
        grades = new ArrayList<>();
        grades.add(new ArrayList<>());
        semester = 1;
    }

    /**
     * getter.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * setter.
     *
     * @param name on which will changed.
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * get all grades in semester.
     *
     * @param semester of needed grades.
     * @return grades of semester.
     * @throws NoSuchSemesterYet semester is higher than current semester.
     */
    public ArrayList<Grade> getGrades(int semester) throws NoSuchSemesterYet {
        if (semester > this.semester) {
            throw new NoSuchSemesterYet("No such semester yet, try .nextSemester()");
        }
        return grades.get(semester - 1);
    }

    /**
     * get all grades from all semesters.
     *
     * @return grades.
     */
    public ArrayList<ArrayList<Grade>> getAllGrades() {
        return new ArrayList<>(grades);
    }

    /**
     * add grade to semester.
     *
     * @param grade    int.
     * @param semester to which will be added grade.
     * @param type     of grade.
     * @throws NoSuchSemesterYet semester is higher than current semester.
     */
    public void setGrade(int grade, int semester, GradeType type) throws NoSuchSemesterYet {
        if (grade > 5 || grade < 2) {
            return;
        }
        if (semester > this.semester) {
            throw new NoSuchSemesterYet("No such semester yet, try .nextSemester()");
        }
        grades.get(semester - 1).add(new Grade(grade, type));
    }

    /**
     * increase current semester.
     */
    public void nextSemester() {
        grades.add(new ArrayList<>());
        this.semester++;
    }

    /**
     * calculate final grade of this subject.
     *
     * @return final grade.
     */
    public int finalResult() {
        List<Integer> exams = grades.stream().flatMap(
                s -> s.stream().filter(
                        a -> a.getType() == GradeType.EXAM
                                || a.getType() == GradeType.DIFFPASS).map(
                        Grade::getGrade)
        ).collect(Collectors.toList());
        return Math.round((float) exams.stream().mapToInt(Integer::intValue).sum() / exams.size());
    }

    /**
     * exception.
     */
    static class NoSuchSemesterYet extends Exception {
        /**
         * called if semester is higher than current semester.
         *
         * @param message which will be printed.
         */
        public NoSuchSemesterYet(String message) {
            super(message);
        }
    }
}
