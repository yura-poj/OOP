package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadsPrimeNumberDetector implements PrimeNumberDetector {

    final int numberThreads;
    Thread[] threads;
    AtomicBoolean[] results;


    public ThreadsPrimeNumberDetector(int numberThreads) {
        this.numberThreads = numberThreads;
        threads = new Thread[numberThreads];
        results = new AtomicBoolean[numberThreads];
    }

    public boolean isPrimeNumberExist(int[] nums) throws InterruptedException {
        int numsLength = nums.length;
        boolean isPrime = false;
        for (int i = 0; i < numsLength; ) {
            for (; i < numsLength; i++) {
                threads[i % numberThreads] = createThread(nums[i], results[i % numberThreads]);
            }
            for (int x = 0; x < 4; x++) {
                threads[x].start();
                threads[x].join();
            }
            for (int x = 0; x < 4; x++) {
                if (results[x].get() ){
                    return true;
                }
            }
        }
        return false;
    }

    private Thread createThread(int number, AtomicBoolean result) {
        return new Thread(() -> {
            result.set(PrimeNumberDetectorUtils.isPrime(number));
        });
    }
}
