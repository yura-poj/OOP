package ru.nsu.pozhidaev;

/**
 * grade of student contain int grade and bool success for pass,
 * and if student got grade more than 2.
 */
public class Grade {
    private final GradeType type;
    private final int grade;
    private boolean success;

    /**
     * constructor, success is true if grade more than 2.
     *
     * @param grade from 2 to 5.
     * @param type  enum GradeType.
     */
    public Grade(int grade, GradeType type) {
        this.type = type;
        success = false;
        this.grade = grade;
        if (grade > 2) {
            success = true;
        }
    }

    /**
     * getter.
     *
     * @return grade from 2 to 5.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * getter.
     *
     * @return if pass is success or grade more than 2 or not.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * getter.
     *
     * @return type of grade.
     */
    public GradeType getType() {
        return type;
    }

    /**
     * return true if object is the same,
     * or if grade and type are equal.
     *
     * @param o object.
     * @return bool equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    /**
     * hashcode by type, grade and success.
     *
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return type.hashCode() + Integer.hashCode(grade) + Boolean.hashCode(success);
    }
}
