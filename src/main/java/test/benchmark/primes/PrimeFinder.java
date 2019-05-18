package test.benchmark.primes;

import java.util.stream.IntStream;

public class PrimeFinder {
    private PrimeFinder() {
        // as this is a utility
        // avoid instantiation of this class
    }

    public static boolean isPrimeSimpleIteration(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isPrimeSimpleIterationStream(int n) {
        return IntStream.range(2, n)
                .noneMatch(e -> n % e == 0);
    }

    public static boolean isPrimeSimpleIterationParallelStream(int n) {
        return IntStream.range(2, n)
                .parallel()
                .noneMatch(e -> n % e == 0);
    }

    public static boolean isPrimeSimpleSqrt(int n) {
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isPrimeSimpleSqrtStream(int n) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(n) + 1))
                .noneMatch(e -> n % e == 0);
    }

    public static boolean isPrimeSimpleSqrtParalllelStream(int n) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(n) + 1))
                .parallel()
                .noneMatch(e -> n % e == 0);
    }

    public static boolean isPrimeEfficient(int n) {
        if (n == 2 || n == 3) return true;
        if (n == 1 || !probablyPrime(n))
            return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isPrimeEfficientStream(int n) {
        if (n == 2 || n == 3) return true;
        if (n == 1 || !probablyPrime(n))
            return false;
        return IntStream.rangeClosed(2, (int) (Math.sqrt(n) + 1))
                .noneMatch(e -> n % e == 0);
    }

    public static boolean isPrimeEfficientParallelStream(int n) {
        if (n == 2 || n == 3) return true;
        if (n == 1 || !probablyPrime(n))
            return false;
        return IntStream.rangeClosed(2, (int) (Math.sqrt(n) + 1))
                .parallel()
                .noneMatch(e -> n % e == 0);
    }

    /**
     * a number (greater than 3) is prime if an only if it can be expresses as
     * either 6x+1 or 6x-1 (this is not true for 2 and 3)
     *
     * @param n
     * @return {@code true} if the number is probably a prime,
     * {@code false} otherwise
     */
    private static boolean probablyPrime(int n) {
        return ((n - 1) % 6 == 0 || (n + 1) % 6 == 0);
    }
}

