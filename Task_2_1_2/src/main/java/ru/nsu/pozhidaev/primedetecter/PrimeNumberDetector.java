package ru.nsu.pozhidaev.prime_detecter;

import java.util.Arrays;
import java.util.List;

/**
 * Class for detecting prime numbers in arrays.
 * <p>
 * This class provides methods to check if any number in an array is prime.
 * It uses the PrimeNumberDetectorUtils class for the actual prime number checking.
 */
public class PrimeNumberDetector {

    /**
     * Checks if any number in the given array is prime.
     * <p>
     * @param nums Array of integers to check
     * @return true if at least one number in the array is prime, false otherwise
     */
    public boolean isPrimeNumberExist(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        for (int num : nums) {
            if (PrimeNumberDetectorUtils.isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Processes a list of numbers and checks if any of them are prime.
     * <p>
     * @param numbers List of numbers to process
     * @return List of boolean values indicating if each number is prime
     */
    public List<Boolean> processNumbers(List<Long> numbers) {
        return PrimeNumberDetectorUtils.processNumbers(numbers);
    }
}
