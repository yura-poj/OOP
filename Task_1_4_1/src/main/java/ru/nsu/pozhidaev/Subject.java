package ru.nsu.pozhidaev;

import java.util.HashMap;

public class Subject {
    private String name;
    private HashMap<String,int[]> types;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public int[] getGrades(String type) {
        if (!types.containsKey(type)) {
            return null;
        }
        return types.get(type);
    }

    public void setGrade(int grade, int semester, String type) {
        if (!types.containsKey(type)){
            types.put(type, new int[8]);
        }
        types.get(type)[semester] = grade;
    }
}
