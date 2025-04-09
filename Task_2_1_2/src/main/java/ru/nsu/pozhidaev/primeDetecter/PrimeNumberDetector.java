package ru.nsu.pozhidaev.primeDetecter;

import java.util.Arrays;

public class PrimeNumberDetector {
        /**
         * function try to find prime number in array.
         *
         * @param nums numbers.
         *
         * @return boolean exist or not.
         *
         */
        public boolean isPrimeNumberExist(int[] nums) {

            return Arrays.stream(nums)
                    .parallel()
                    .anyMatch(PrimeNumberDetectorUtils::isPrime);
        }
}
