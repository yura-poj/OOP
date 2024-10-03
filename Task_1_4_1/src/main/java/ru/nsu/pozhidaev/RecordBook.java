package ru.nsu.pozhidaev;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * record book that contain subjects and their grades,
 * student should have one unique record book.
 */
public class RecordBook {
    private final String studentName;
    private final HashMap<String, Subject> subjects;
    private int currentSemester;

    /**
     * constructor.
     *
     * @param studentName name of owner.
     */
    public RecordBook(String studentName) {
        this.studentName = studentName;
        subjects = new HashMap<>();
        currentSemester = 1;
    }

    /**
     * getter.
     *
     * @return student name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * increase current semester and increase it for all subjects in hashtable.
     */
    public void nextSemester() {
        currentSemester++;
        for (Subject subject : subjects.values()) {
            subject.nextSemester();
        }
    }

    /**
     * add grade for student to record book.
     *
     * @param subject   string of name of existing subject in hashtable.
     * @param grade     number that means success of grade.
     * @param semester  to which need to add grade to subject.
     * @param gradeType enum type of grade.
     * @throws Subject.NoSuchSemesterYet if current semester is smaller than called.
     * @throws NoSuchSubjectYet          if such subject wasn't found in hashtable.
     */
    public void addGrade(
            String subject, int grade, int semester, GradeType gradeType
    ) throws Subject.NoSuchSemesterYet, NoSuchSubjectYet {

        if (!subjects.containsKey(subject)) {
            throw new NoSuchSubjectYet("No such subject yet, try addSubject()");
        }
        subjects.get(subject).setGrade(grade, semester, gradeType);
    }

    /**
     * add grade with type pass.
     *
     * @param subject  string of name of existing subject in hashtable.
     * @param pass     bool success or not.
     * @param semester to which need to add grade to subject.
     * @throws Subject.NoSuchSemesterYet if current semester is smaller than called.
     * @throws NoSuchSubjectYet          if such subject wasn't found in hashtable.
     */
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

    /**
     * getter.
     *
     * @return current semester.
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     * add string of new subject into hashtable.
     *
     * @param subject string of name of subject.
     */
    public void addSubject(String subject) {
        subjects.put(subject, new Subject(subject));
    }

    /**
     * get average grade point.
     * counting by collect all grades without passes and deviding them on their number.
     *
     * @return result sum devided on size of grades.
     */
    public double gradePointAverage() {
        List<Grade> grades = gradeStream(0, currentSemester).filter(
                b -> !b.getType().equals(GradeType.PASS)).collect(Collectors.toList());

        int sum = grades.stream().map(a -> a.getGrade()).reduce(0, (res, x) -> res + x);

        return (double) sum / grades.size();
    }

    /**
     * chance to study free calculted by collect results on exams by last two semesters,
     * and check if there is no three grades.
     *
     * @return can student study free or not.
     */
    public boolean chanceToStudyFree() {
        int skipNum = 0;
        if (currentSemester > 2) {
            skipNum = currentSemester - 2;
        }
        boolean haveThreeOnExam = gradeStream(skipNum, 2).filter(
                b -> b.getType().equals(GradeType.EXAM)).anyMatch(s -> s.getGrade() == 3);

        boolean allSuccess = gradeStream(skipNum, 2).filter(
                Objects::nonNull).allMatch(Grade::isSuccess);

        return (!haveThreeOnExam && allSuccess);
    }

    /**
     * chance to get red diploma calculated by collecting all final grade of exams and diffpasses,
     * if there is no three grades, and number of fives is more than 74 percentage,
     * and have excellent qualification work.
     *
     * @return can student get red diploma or not.
     */
    public boolean chanceToGetRedDiploma() {
        boolean haveThreeOnExam = gradeStream(0, currentSemester).filter(
                b -> b.getType().equals(GradeType.EXAM)
                || b.getType().equals(GradeType.DIFFPASS)).anyMatch(s -> s.getGrade() == 3);

        boolean allSuccess = gradeStream(0, currentSemester).filter(
                Objects::nonNull).allMatch(Grade::isSuccess);

        boolean qualifyExcellentWork = gradeStream(0, currentSemester).filter(
                    b -> b.getType().equals(GradeType.QUALIFICATION)).findAny().map(
                        s -> s.getGrade() == 5).orElse(false);

        List<Integer> finalResults = subjects.values().stream().limit(currentSemester).map(
                Subject::finalResult).collect(Collectors.toList());


        return (!haveThreeOnExam && allSuccess && qualifyExcellentWork && (
                finalResults.stream().mapToInt(Integer::intValue).sum() / finalResults
                        .size() > 0.75));
    }

    /**
     * chance to increase student grant calculated by collecting all grades,
     * if there is no grades less than 5, student will get higher grant.
     *
     * @return can student have higher grant or not.
     */
    public boolean chanceToGetHigherGrants() {
        return gradeStream(currentSemester - 1, 1).allMatch(s -> s.getGrade() == 5);
    }

    /**
     * creating stream for functions.
     *
     * @param skip  how many semesters should it skip.
     * @param limit how many semesters they need.
     * @return stream of grades.
     */
    private Stream<Grade> gradeStream(int skip, int limit) {
        return subjects.values().stream().flatMap(
                s -> s.getAllGrades().stream().skip(skip).limit(limit).flatMap(
                        a -> a.stream().filter(Objects::nonNull)
                )
        );
    }

    /**
     * exception.
     */
    static class NoSuchSubjectYet extends Exception {
        /**
         * called if hashtable has no such subject.
         *
         * @param message that should be printed.
         */
        NoSuchSubjectYet(String message) {
            super(message);
        }
    }
}

