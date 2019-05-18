package test.benchmark.primes;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*

Result of benchmarking:

Benchmark                                                   Mode  Cnt     Score     Error  Units
PrimeFinderBenchmark.isPrimeEfficient                      thrpt    5  1401.380 ±  68.038  ops/s
PrimeFinderBenchmark.isPrimeEfficientParallelStream        thrpt    5    16.860 ±   2.564  ops/s
PrimeFinderBenchmark.isPrimeEfficientStream                thrpt    5   821.062 ±  33.726  ops/s
PrimeFinderBenchmark.isPrimeSimpleIteration                thrpt    5    26.924 ±   0.961  ops/s
PrimeFinderBenchmark.isPrimeSimpleIterationParallelStream  thrpt    5     2.371 ±   0.357  ops/s
PrimeFinderBenchmark.isPrimeSimpleIterationStream          thrpt    5    11.162 ±   2.592  ops/s
PrimeFinderBenchmark.isPrimeSimpleSqrt                     thrpt    5  1515.956 ± 307.568  ops/s
PrimeFinderBenchmark.isPrimeSimpleSqrtParalllelStream      thrpt    5     6.868 ±   0.800  ops/s
PrimeFinderBenchmark.isPrimeSimpleSqrtStream               thrpt    5   720.639 ±   3.864  ops/s

 */
public class PrimeFinderBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeEfficient(BenchmarkState benchmarkState,
                                 Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeEfficient(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleIteration(BenchmarkState benchmarkState,
                                       Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleIteration(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleSqrt(BenchmarkState benchmarkState,
                                  Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleSqrt(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeEfficientStream(BenchmarkState benchmarkState,
                                       Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeEfficientStream(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleIterationStream(BenchmarkState benchmarkState,
                                             Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleIterationStream(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleSqrtStream(BenchmarkState benchmarkState,
                                        Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleSqrtStream(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeEfficientParallelStream(BenchmarkState benchmarkState,
                                               Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeEfficientParallelStream(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleIterationParallelStream(BenchmarkState benchmarkState,
                                                     Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleIterationParallelStream(e))
        );
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Fork(1)
    public void isPrimeSimpleSqrtParalllelStream(BenchmarkState benchmarkState,
                                                 Blackhole blackhole) {
        benchmarkState.list.forEach(e ->
                blackhole.consume(PrimeFinder.isPrimeSimpleSqrtParalllelStream(e))
        );
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Integer> list;

        @Setup
        public void setup() {
            list = IntStream.rangeClosed(10000, 20000)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }


}
