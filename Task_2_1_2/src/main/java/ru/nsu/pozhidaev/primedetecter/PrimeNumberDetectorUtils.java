package ru.nsu.pozhidaev.primedetecter;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for prime number detection operations.
 * 
 * <p>This class provides methods for checking if a number is prime
 * and processing lists of numbers to find prime numbers.
 * 
 * <p>The class uses efficient algorithms for prime number detection
 * and provides thread-safe operations for processing multiple numbers.
 */
public class PrimeNumberDetectorUtils {

    /**
     * Checks if a given number is prime.
     * 
     * <p>A number is considered prime if it is greater than 1
     * and has no positive divisors other than 1 and itself.
     * 
     * <p>@param number The number to check for primality
     *
     * <p>@return true if the number is prime, false otherwise
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
     * 
     * <p>This method efficiently processes a list of numbers and returns
     * a list of boolean values indicating whether each number is prime.
     * 
     * <p>@param numbers List of numbers to process
     *
     * <p>@return List of boolean values indicating primality of each number
     */
    public static List<Boolean> processNumbers(List<Long> numbers) {
        List<Boolean> results = new ArrayList<>();
        for (Long number : numbers) {
            results.add(isPrime(number));
        }
        return results;
    }
}