package ru.nsu.pozhidaev;

public enum Status {
    PROCESSING("processing"),
    COOKING("cooking"),
    COOKED("cooked"),
    DELIVERING("delivering"),
    DELIVERED("delivered");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

