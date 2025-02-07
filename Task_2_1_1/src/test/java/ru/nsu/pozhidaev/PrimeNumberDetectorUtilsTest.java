package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PrimeNumberDetectorUtilsTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11})
    void testIsPrime(int number) {
        assertTrue(PrimeNumberDetectorUtils.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 8, 122, 144, 49})
    void testIsNotPrime(int number) {
        assertFalse(PrimeNumberDetectorUtils.isPrime(number));
    }
}