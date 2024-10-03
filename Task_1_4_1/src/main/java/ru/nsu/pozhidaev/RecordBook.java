package ru.nsu.pozhidaev;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordBook {
    private final String studentName;
    private final HashMap<String, Subject> subjects;
    private int currentSemester;

    public RecordBook(String studentName) {
        this.studentName = studentName;
        subjects = new HashMap<>();
        currentSemester = 1;
    }

    public String getStudentName() {
        return studentName;
    }

    public void nextSemester() {
        currentSemester++;
        for (Subject subject : subjects.values()) {
            subject.nextSemester();
        }
    }

    public void addGrade(
            String subject, int grade, int semester, GradeType gradeType
    ) throws Subject.NoSuchSemesterYet, NoSuchSubjectYet {

        if (!subjects.containsKey(subject)) {
            throw new NoSuchSubjectYet("No such subject yet, try addSubject()");
        }
        subjects.get(subject).setGrade(grade, semester, gradeType);
    }

    public void addPass(
            String subject, boolean pass, int semester
    ) throws Subject.NoSuchSemesterYet, NoSuchSubjectYet {
        if (!subjects.containsKey(subject)) {
            throw new NoSuchSubjectYet("No such subject yet, try addSubject()");
        }
        int grade = 2;
        if (pass) {
            grade = 5;
        }
        subjects.get(subject).setGrade(grade, semester, GradeType.PASS);
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void addSubject(String subject) {
        subjects.put(subject, new Subject(subject));
    }

    public double gradePointAverage() {
        List<Grade> grades = gradeStream(0,currentSemester).filter(b-> ! b.getType().equals(GradeType.PASS)).collect(Collectors.toList());

        int sum = grades.stream().map(a -> a.getGrade()).reduce(0, (res, x) -> res + x);

        return (double) sum / grades.size();
    }

    public boolean chanceToStudyFree() {
        int skipNum = 0;
        if(currentSemester > 2) {
            skipNum = currentSemester - 2;
        }
        boolean haveThreeOnExam = gradeStream(skipNum,2).filter(b-> b.getType().equals(GradeType.EXAM)).anyMatch(s->s.getGrade() == 3);

        boolean allSuccess = gradeStream(skipNum, 2).filter(Objects::nonNull).allMatch(Grade::isSuccess);

        return (!haveThreeOnExam && allSuccess);
    }

    public boolean chanceToGetRedDiploma() {
        boolean haveThreeOnExam = gradeStream(0,currentSemester).filter(b-> b.getType().equals(GradeType.EXAM)
                || b.getType().equals(GradeType.DIFFPASS)).anyMatch(s->s.getGrade() == 3);

        boolean allSuccess = gradeStream(0,currentSemester).filter(Objects::nonNull).allMatch(Grade::isSuccess);

        boolean qualifyExcellentWork = gradeStream(0,currentSemester).filter(
                b->b.getType().equals(GradeType.QUALIFICATION)).findAny().map(
                        s->s.getGrade() == 5).orElse(false);

        List<Integer> finalResults = subjects.values().stream().limit(currentSemester).map(
                Subject::finalResult).collect(Collectors.toList());



        return (!haveThreeOnExam && allSuccess && qualifyExcellentWork &&
            (finalResults.stream().mapToInt(Integer::intValue).sum() / finalResults.size() > 0.75));
    }

    public boolean chanceToGetHigherGrants() {
        return gradeStream(currentSemester - 1, 1).allMatch(s->s.getGrade() == 5);
    }

    private Stream<Grade> gradeStream(int skip, int limit) {
        return subjects.values().stream().flatMap(
                s-> s.getAllGrades().stream().skip(skip).limit(limit).flatMap(
                        a -> a.stream().filter(Objects::nonNull)
                )
        );
    }
    static class NoSuchSubjectYet extends Exception {
        NoSuchSubjectYet(String message) {
            super(message);
        }
    }
}

