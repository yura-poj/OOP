package ru.nsu.pozhidaev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Subject{
    private String name;
    private ArrayList<ArrayList<Grade>> grades;
    private int semester;

    public Subject(String name) {
        this.name = name;
        grades = new ArrayList<>();
        grades.add(new ArrayList<>());
        semester = 1;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public ArrayList<Grade> getGrades(int semester) throws NoSuchSemesterYet {
        if(semester > this.semester) {
            throw new NoSuchSemesterYet("No such semester yet, try .nextSemester()");
        }
        return grades.get(semester-1);
    }

    public ArrayList<ArrayList<Grade>> getAllGrades() {
        return new ArrayList<>(grades);
    }

    public void setGrade(int grade, int semester, GradeType type) throws NoSuchSemesterYet {
        if(semester > this.semester) {
            throw new NoSuchSemesterYet("No such semester yet, try .nextSemester()");
        }
        grades.get(semester-1).add(new Grade(grade, type));
    }

    public void nextSemester() {
        grades.add(new ArrayList<>());
        this.semester++;
    }

    public int finalResult(){
        List<Integer> exams =  grades.stream().flatMap(
                s->s.stream().filter(
                        a->a.getType() == GradeType.EXAM || a.getType() == GradeType.DIFFPASS).map(
                        Grade::getGrade)
        ).collect(Collectors.toList());
        return Math.round((float) exams.stream().mapToInt(Integer::intValue).sum() / exams.size());
    }

    static class NoSuchSemesterYet extends Exception{
        public NoSuchSemesterYet(String message) {
            super(message);
        }
    }
}
