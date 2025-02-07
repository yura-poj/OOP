package ru.nsu.pozhidaev;


public class PrimeNumberDetectorUtils {
    static boolean isPrime(int number) {
        for (int divider = 2; divider <= Math.sqrt(number); divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }
}
