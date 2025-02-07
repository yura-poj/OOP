package ru.nsu.pozhidaev;

import java.util.HashSet;
import java.util.Set;

/**
 * class realizes detector of prime number.
 */

public class ThreadsPrimeNumberDetector implements PrimeNumberDetector {

    private final int numberThreads;
    private final Thread[] threads;
    private boolean[] results;

    /**
     * constructor.
     *
     * @param numberThreads number of threads that will be created.
     */
    public ThreadsPrimeNumberDetector(int numberThreads) {
        this.numberThreads = numberThreads;
        threads = new Thread[numberThreads];
        results = new boolean[numberThreads];
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
            results[i] = false;
            threads[i].start();
        }

        Set finishedThreads = new HashSet();
        while (finishedThreads.size() < numberThreads) {
            for (int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
                if (!threads[threadNumber].isAlive()) {
                    if (results[threadNumber]) {
                        return true;
                    } else {
                        finishedThreads.add(threadNumber);
                    }
                }
            }
        }
        return false;
    }

    private void searchInSubArray(int[] nums, int start, int end, int result) {
        for (int index = start; index < end; index++) {
            if (PrimeNumberDetectorUtils.isPrime(nums[index])) {
                results[result] = true;
                return;
            }
        }

        results[result] = false;
    }

    private Thread createThread(int[] nums, int result, int start, int end) {
        return new Thread(() -> searchInSubArray(nums, start, end, result));
    }
}
