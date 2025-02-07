package ru.nsu.pozhidaev;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadsPrimeNumberDetector implements PrimeNumberDetector {

    private final int numberThreads;
    private final Thread[] threads;
    private boolean results[];

    public ThreadsPrimeNumberDetector(int numberThreads) {
        this.numberThreads = numberThreads;
        threads = new Thread[numberThreads];
        results = new boolean[numberThreads];
    }

    @Override
    public boolean isPrimeNumberExist(int[] nums) throws InterruptedException {
        int numsLength = nums.length;
        int start = 0;
        int end = 0;
        int change = numsLength / numberThreads;
        for (int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
            end = (threadNumber == numberThreads - 1) ? numsLength - 1 : start + change;
            threads[threadNumber] = createThread(nums, threadNumber, start, end);
            start += change;
        }
        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }

        Set finishedThreads = new HashSet();
        while (finishedThreads.size() < numberThreads) {
            for(int threadNumber = 0; threadNumber < numberThreads; threadNumber++) {
                if(! threads[threadNumber].isAlive())  {
                    if(results[threadNumber]) {
                        return true;
                    }
                    else {
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
            }
        }

        results[result] = true;
    }

    private Thread createThread(int[] nums, int result, int start, int end) {
        return new Thread(() -> searchInSubArray(nums, start, end, result));
    }
}
