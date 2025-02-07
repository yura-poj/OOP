package ru.nsu.pozhidaev;

import java.util.Arrays;

/**
 * class realizes detector of prime number.
 */
public class ParallelsPrimeNumberDetector implements PrimeNumberDetector {

    /**
     * function try to find prime number in array.
     *
     * @param nums numbers.
     *
     * @return boolean exist or not.
     *
     */
    public boolean isPrimeNumberExist(int[] nums) {

        return Arrays.stream(nums)
                .parallel()
                .anyMatch(PrimeNumberDetectorUtils::isPrime);
    }
}
