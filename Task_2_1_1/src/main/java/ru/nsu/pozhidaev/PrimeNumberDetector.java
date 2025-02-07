package ru.nsu.pozhidaev;

/**
 * interface realizes detector of prime number.
 */
public interface PrimeNumberDetector {
    /**
     * function try to find prime number in array.
     *
     * @param nums numbers.
     *
     * @return boolean exist or not.
     *
     */
    public boolean isPrimeNumberExist(int[] nums) throws InterruptedException;
}