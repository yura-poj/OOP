package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PrimeNumberDetectorTest {
    static final int NUMBER_OF_ARRAY_TEST1 = 1000;
    static final int NUMBER_OF_ARRAY_TEST2 = 10000;

    void isPrimeNumberExistWithTime(int[] nums,
                                    PrimeNumberDetector detector) throws InterruptedException {
        long start = System.nanoTime();
        detector.isPrimeNumberExist(nums);
        System.out.println(detector.getClass().getSimpleName()
                + " compute in " + (System.nanoTime() - start));
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
        int[] nums = new int[NUMBER_OF_ARRAY_TEST1 + 1];
        int x = 2;
        int y = 2;
        for (int i = 0; i < NUMBER_OF_ARRAY_TEST1; i++) {
            x++;
            y++;
            nums[i] = x * y;
        }
        nums[NUMBER_OF_ARRAY_TEST1] = 149;
        isPrimeNumberExistWithTime(nums, detector);
        assertTrue(detector.isPrimeNumberExist(nums));
    }

    @ParameterizedTest
    @MethodSource("provideDetectors")
    void bigTestHavePrime(PrimeNumberDetector detector) throws InterruptedException {
        int[] nums = new int[NUMBER_OF_ARRAY_TEST2 + 1];
        int x = 2;
        int y = 2;
        for (int i = 0; i < NUMBER_OF_ARRAY_TEST2; i++) {
            x++;
            y++;
            nums[i] = x * y;
        }
        nums[NUMBER_OF_ARRAY_TEST2] = 149;
        isPrimeNumberExistWithTime(nums, detector);
        assertTrue(detector.isPrimeNumberExist(nums));
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
