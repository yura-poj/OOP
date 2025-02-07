package ru.nsu.pozhidaev;

/**
 * class realizes detector of prime number.
 */

public class SequentialPrimeNumberDetector implements PrimeNumberDetector {
    /**
     * function try to find prime number in array.
     *
     * @param nums numbers.
     *
     * @return boolean exist or not.
     *
     */
    public boolean isPrimeNumberExist(int[] nums) {
        for (int arg : nums) {
            if (PrimeNumberDetectorUtils.isPrime(arg)) {
                return true;
            }
        }
        return false;
    }
}
