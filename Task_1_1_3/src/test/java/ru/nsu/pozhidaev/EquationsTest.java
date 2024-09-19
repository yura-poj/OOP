package ru.nsu.pozhidaev;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.Test;



class EquationsTest {
    Equations eq = new Equations();

    @Test
    void equations() {
        assertTimeout(ofMillis(10), () -> eq.equations());
    }
}