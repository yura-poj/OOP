package ru.nsu.pozhidaev.prime_detecter;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for prime number detection operations.
 * Provides helper methods for checking if numbers are prime.
 */
public class PrimeNumberDetectorUtils {

    /**
     * Checks if a given number is prime.
     * <p>
     * A prime number is a natural number greater than 1 that has no positive divisors other than 1 and itself.
     * <p>
     * @param number The number to check
     * @return true if the number is prime, false otherwise
     */
    public static boolean isPrime(long number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (long i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes a list of numbers and checks if any of them are prime.
     * <p>
     * @param numbers List of numbers to process
     * @return List of boolean values indicating if each number is prime
     */
    public static List<Boolean> processNumbers(List<Long> numbers) {
        List<Boolean> results = new ArrayList<>();
        for (Long number : numbers) {
            results.add(isPrime(number));
        }
        return results;
    }
}