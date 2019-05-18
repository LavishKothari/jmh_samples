package test.benchmark.mod;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * This is a benchmark to see how efficient
 * bitwise operators is as compared to modulus operator
 */
public class ModBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(1)
    public int testModulusOperator(BenchmarkState benchmarkState) {
        return benchmarkState.x % benchmarkState.mod;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(1)
    public int testBitwiseOperator(BenchmarkState benchmarkState) {
        return benchmarkState.x & (benchmarkState.mod - 1);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(1)
    public int testModulusOperatorLocalFields() {
        int x = 1234567;
        int mod = 1024;
        return x % mod;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(1)
    public int testBitwiseOperatorLocalFields() {
        int x = 1234567;
        int mod = 1023;
        return x & mod;
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        int x = 1234567; // don't declare this final
        int mod = 1024; // don't declare this final
    }

}
