package ru.nsu.pozhidaev;

/**
 * student - owner of record book.
 */
public class Student {
    private final String name;
    private final RecordBook recordBook;

    /**
     * contructor.
     *
     * @param name of student.
     */
    public Student(String name) {
        this.name = name;
        recordBook = new RecordBook(name);
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
     * getter.
     *
     * @return record book
     */
    public RecordBook getRecordBook() {
        return recordBook;
    }
}
