package ru.nsu.pozhidaev;

public enum Status {
    PROCESSING("processing"),
    COOKING("cooking"),
    COOKED("cooked"),
    DELIVERING("delivering"),
    DELIVERED("delivered");

    private final String description;

    Status(String russianName) {
        this.description = russianName;
    }

    public void printStatus (int index) {
        System.out.println("Status of order №" + index + " is: " + description);
    }
}

