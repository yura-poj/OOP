package ru.nsu.pozhidaev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeNumberDetectorTest {

    void checkTime(int[] nums, PrimeNumberDetector detector) throws InterruptedException {
        long start = System.nanoTime();
        assertTrue(detector.isPrimeNumberExist(nums));
        System.out.println(detector.getClass().getSimpleName() + " compute in " + (System.nanoTime() - start));
    }

    @ParameterizedTest
    @MethodSource("provideDetectors")
    void testHaveNotPrime(PrimeNumberDetector detector) throws InterruptedException {
        int[] nums = {12, 4, 144, 412, 54, 49};
        assertFalse(detector.isPrimeNumberExist(nums));
    }

    @ParameterizedTest
    @MethodSource("provideDetectors")
    void testHavePrime(PrimeNumberDetector detector) throws InterruptedException {
        int[] nums = new int[1001];
        int x = 2,y = 2;
        for( int i = 0; i < 1000; i++){
            x++;
            y++;
            nums[i] = x*y;
        }
        nums[1000] = 149;
        checkTime(nums, detector);
    }

    @ParameterizedTest
    @MethodSource("provideDetectors")
    void bigTestHavePrime(PrimeNumberDetector detector) throws InterruptedException {
        int[] nums = new int[10001];
        int x = 2,y = 2;
        for( int i = 0; i < 10000; i++){
            x++;
            y++;
            nums[i] = x*y;
        }
        nums[10000] = 149;
        checkTime(nums, detector);
    }

    static Stream<PrimeNumberDetector> provideDetectors() {
        return Stream.of(
                new SequentialPrimeNumberDetector(),
                new ThreadsPrimeNumberDetector(1),
                new ThreadsPrimeNumberDetector(2),
                new ThreadsPrimeNumberDetector(3),
                new ThreadsPrimeNumberDetector(4),
                new ParallelsPrimeNumberDetector()
        );
    }
}
