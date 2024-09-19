package ru.nsu.pozhidaev;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class EquationsTest {
    Equations eq = new Equations();

    @Test
    void equations() {
        assertTimeout(ofMillis(10), () -> eq.equations());
    }
}