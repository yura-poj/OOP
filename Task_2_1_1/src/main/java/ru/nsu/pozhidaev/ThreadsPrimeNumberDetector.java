package ru.nsu.pozhidaev;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadsPrimeNumberDetector implements PrimeNumberDetector {

    private final int numberThreads;
    private final Thread[] threads;
    private final AtomicBoolean[] results;

    public ThreadsPrimeNumberDetector(int numberThreads) {
        this.numberThreads = numberThreads;
        threads = new Thread[numberThreads];
        results = new AtomicBoolean[numberThreads];
        for (int i = 0; i < numberThreads; i++) {
            results[i] = new AtomicBoolean(false);
        }
    }

    @Override
    public boolean isPrimeNumberExist(int[] nums) throws InterruptedException {
        int numsLength = nums.length;

        for (int i = 0; i < numsLength; i += numberThreads) {
            int threadsToRun = Math.min(numberThreads, numsLength - i);

            for (int x = 0; x < threadsToRun; x++) {
                int index = i + x;
                results[x].set(false);
                threads[x] = createThread(nums[index], results[x]);
            }

            for (int x = 0; x < threadsToRun; x++) {
                threads[x].start();
            }

            for (int x = 0; x < threadsToRun; x++) {
                threads[x].join();
            }

            // Проверяем результаты
            for (int x = 0; x < threadsToRun; x++) {
                if (results[x].get()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Thread createThread(int number, AtomicBoolean result) {
        return new Thread(() -> result.set(PrimeNumberDetectorUtils.isPrime(number)));
    }
}
