package ru.nsu.pozhidaev;

public class Grade {
    private GradeType type;

    private int grade;
    private boolean success;

    public Grade(int grade, GradeType type) {
        this.type = type;
        success = false;
        this.grade = grade;
        if(grade > 2) {
            success = true;
        }
    }

    public int getGrade() {
        return grade;
    }

    public boolean isSuccess() {
        return success;
    }

    public GradeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grade grade1 = (Grade) o;
        return grade == grade1.grade && success == grade1.success;
    }
}
