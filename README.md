# jmh_samples
This repository contains some of the samples and instructions about how to properly write good benchmarks in JMH 

## In Intellij

Install the jmh plugin in intellij. This plugin makes the running of benchmarks very easy.

## Some minor notes:

* Benchmark classes should be in a package other than default.
* `@State` classes should be `public`.

## Useful Annotations 

* `@Benchmark`
* `@BenchmarkMode`
  * `@BenchmarkMode(Mode.Throughput)`
  * `@BenchmarkMode(Mode.AverageTime)`
  * `@BenchmarkMode(Mode.SampleTime)`
  * `@BenchmarkMode(Mode.SingleShotTime)`
  * `@BenchmarkMode(Mode.All)`
* `@OutputTimeUnit(TimeUnit.NANOSECONDS)`
* `@Fork(1)`

## Different Benchmarks

```java
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
```

Benchmark output:
```bash
Benchmark                                     Mode  Cnt    Score    Error   Units
ModBenchmark.testBitwiseOperator             thrpt    5  276.568 ±  2.251  ops/us
ModBenchmark.testBitwiseOperatorLocalFields  thrpt    5  298.264 ± 20.584  ops/us
ModBenchmark.testModulusOperator             thrpt    5  128.097 ± 14.451  ops/us
ModBenchmark.testModulusOperatorLocalFields  thrpt    5  296.364 ± 21.422  ops/us
```

But when you declare the members in the `@State` class as `final`, the corresponding benchmark outputs are as follows:
```bash
Benchmark                          Mode  Cnt    Score    Error   Units
ModBenchmark.testBitwiseOperator  thrpt    5  290.009 ± 77.103  ops/us
ModBenchmark.testModulusOperator  thrpt    5  296.254 ± 43.096  ops/us
```

**So remember that you should never make the members of `@State` class as final. (But it's generally a good programming practice to declare members `final`).**
