package ru.nsu.pozhidaev.primedetecter;

import java.util.List;

/**
 * Detector for prime numbers in arrays.
 * 
 * <p>This class provides functionality to check if any number in an array
 * is prime using efficient algorithms.
 * 
 * <p>The class uses PrimeNumberDetectorUtils for the actual prime number
 * detection, providing a higher-level interface for array processing.
 * 
 * <p>@param nums Array of numbers to check for primality
 *
 * <p>@return true if any number in the array is prime, false otherwise
 */
public class PrimeNumberDetector {

    /**
     * Checks if any number in the given array is prime.
     * 
     * <p>This method efficiently processes an array of numbers and returns
     * true if any of them are prime, false otherwise.
     * 
     * <p>@param nums Array of numbers to check
     *
     * <p>@return true if any number is prime, false otherwise
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
     * 
     * <p>This method provides an alternative interface for processing
     * lists of numbers instead of arrays.
     * 
     * <p>@param numbers List of numbers to process
     *
     * <p>@return List of boolean values indicating primality of each number
     */
    public List<Boolean> processNumbers(List<Long> numbers) {
        return PrimeNumberDetectorUtils.processNumbers(numbers);
    }
}
