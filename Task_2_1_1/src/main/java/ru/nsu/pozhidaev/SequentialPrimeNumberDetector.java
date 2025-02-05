package ru.nsu.pozhidaev;

public class SequentialPrimeNumberDetector implements PrimeNumberDetector {
    public boolean isPrimeNumberExist(int[] nums) {
        for (int arg : nums) {
            if (PrimeNumberDetectorUtils.isPrime(arg)) {
                return true;
            }
        }
        return false;
    }
}
