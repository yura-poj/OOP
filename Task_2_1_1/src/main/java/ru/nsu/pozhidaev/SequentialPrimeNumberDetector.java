package ru.nsu.pozhidaev;

public class SequentialPrimeNumberDetector implements PrimeNumberDetector {
    public boolean isPrimeNumberExist(int[] args) {
        for (int arg : args) {
            if (PrimeNumberDetectorUtils.isPrime(arg)) {
                return true;
            }
        }
        return false;
    }
}
