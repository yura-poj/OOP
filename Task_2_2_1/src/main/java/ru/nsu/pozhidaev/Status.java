package ru.nsu.pozhidaev;

/**
 * status of order.
 */
public enum Status {
    PROCESSING("processing"),
    COOKING("cooking"),
    COOKED("cooked"),
    DELIVERING("delivering"),
    DELIVERED("delivered");

    private final String description;

    /**
     * constructor.
     *
     * @param description of status of order.
     */
    Status(String description) {
        this.description = description;
    }

    /**
     * getter.
     *
     * @return description of status of order.
     */
    public String getDescription() {
        return description;
    }
}

