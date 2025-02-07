package ru.nsu.pozhidaev;

import java.util.HashSet;
import java.util.Set;

/**
 * class realizes detector of prime number.
 */

public class ThreadsPrimeNumberDetector implements PrimeNumberDetector {

    private final int numberThreads;
    private final Thread[] threads;
    private boolean result;

    /**
     * constructor.
     *
     * @param numberThreads number of threads that will be created.
     */
    public ThreadsPrimeNumberDetector(int numberThreads) {
        this.numberThreads = numberThreads;
        threads = new Thread[numberThreads];
    }

    /**
     * function try to find prime number in array.
     *
     * @param nums numbers.
     *
     * @return boolean exist or not.
     *
     * @throws InterruptedException if thread was interrupted outside.
     */

    @Override
    public boolean isPrimeNumberExist(int[] nums) throws InterruptedException {
        result = false;
        int numsLength = nums.length;
        int start = 0;
        int end = 0;
        int change = numsLength / numberThreads;
        for (int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
            end = (threadNumber == numberThreads - 1) ? numsLength : start + change;
            threads[threadNumber] = createThread(nums, threadNumber, start, end);
            start += change;
        }
        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }

        for (int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
            threads[threadNumber].join();
        }
        for (int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
            if (result) {
                return true;
            }
        }
        return false;
    }

    private void searchInSubArray(int[] nums, int start, int end) {
        for (int index = start; index < end; index++) {
            if (Thread.interrupted()) {
                return;
            }
            if (PrimeNumberDetectorUtils.isPrime(nums[index])) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                result = true;
                return;
            }
        }
    }

    private Thread createThread(int[] nums, int result, int start, int end) {
        return new Thread(() -> searchInSubArray(nums, start, end));
    }
}
