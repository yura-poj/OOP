package ru.nsu.pozhidaev;


/**
 * class compute is this prime number or not.
 */

public class PrimeNumberDetectorUtils {
    /**
     * function compute is this prime number or not.
     *
     * @param number to check.
     *
     * @return boolean is number prime or not.
     */
    static boolean isPrime(int number) {
        for (int divider = 2; divider <= Math.sqrt(number); divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }
}
