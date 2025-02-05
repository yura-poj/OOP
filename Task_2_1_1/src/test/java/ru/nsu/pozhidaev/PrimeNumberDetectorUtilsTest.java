package ru.nsu.pozhidaev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrimeNumberDetectorUtilsTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11}) // Передача параметров
    void testIsPrime(int number) {
        assertTrue(PrimeNumberDetectorUtils.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 8, 122, 144, 49}) // Передача параметров
    void testIsNotPrime(int number) {
        assertFalse(PrimeNumberDetectorUtils.isPrime(number));
    }
}