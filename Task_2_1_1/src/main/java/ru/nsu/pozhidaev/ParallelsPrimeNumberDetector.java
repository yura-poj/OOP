package ru.nsu.pozhidaev;

import java.util.Arrays;
import java.util.stream.Stream;

public class ParallelsPrimeNumberDetector {
    public boolean isPrimeNumberExist(int[] nums) {

        return Arrays.stream(nums)
                .parallel()
                .anyMatch(PrimeNumberDetectorUtils::isPrime);
    }
}
