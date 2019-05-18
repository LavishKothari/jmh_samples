package test.benchmark.primes;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PrimeFinderTest {
    @Test
    public void testEfficient() {
        Assert.assertFalse(PrimeFinder.isPrimeEfficient(1));
        Assert.assertTrue(PrimeFinder.isPrimeEfficient(2));
        Assert.assertTrue(PrimeFinder.isPrimeEfficient(3));
        Assert.assertFalse(PrimeFinder.isPrimeEfficient(4));
        Assert.assertTrue(PrimeFinder.isPrimeEfficient(5));

        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeEfficient, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleIteration, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleSqrt, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeEfficientStream, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleIterationStream, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleSqrtStream, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeEfficientParallelStream, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleIterationParallelStream, 1, 100));
        Assert.assertEquals(25,
                getCount(PrimeFinder::isPrimeSimpleSqrtParalllelStream, 1, 100));

    }

    private long getCount(IntPredicate intPredicate, int start, int end) {
        return IntStream.range(1, 100)
                .filter(PrimeFinder::isPrimeEfficient)
                .count();
    }
}
