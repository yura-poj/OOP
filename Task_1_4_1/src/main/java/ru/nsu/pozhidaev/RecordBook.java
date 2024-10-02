package ru.nsu.pozhidaev;

import java.util.HashMap;

public class RecordBook {
    private String studentName;
    private HashMap<String,Subject> subjects;

    public RecordBook(String studentName) {
        this.studentName = studentName;
        subjects = new HashMap<>();
    }

    public double gradePointAverage(){
        return 5.0;
    }

    public boolean chanceToStudyFree(){
        return true;
    }

    public boolean chanceToGetRedDiploma(){
        return true;
    }

    public boolean chanceToGetHigherGrants(){
        return true;
    }
}
