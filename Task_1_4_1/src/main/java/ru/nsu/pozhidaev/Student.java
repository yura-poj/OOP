package ru.nsu.pozhidaev;

public class Student {
    private String name;
    private RecordBook recordBook;

    public Student(String name) {
        this.name = name;
        recordBook = new RecordBook(name);
    }

    public String getName() {
        return name;
    }

    public RecordBook getRecordBook() {
        return recordBook;
    }
}
