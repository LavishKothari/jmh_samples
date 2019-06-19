# jmh_samples
This repository contains some of the samples and instructions about how to properly write good benchmarks in JMH 

## In Intellij

Install the jmh plugin in intellij. This plugin makes the running of benchmarks very easy.

## Some minor notes:

* Benchmark classes should be in a package other than default.
* `@State` classes should be `public` (otherwise you will get complie time error).

## Useful Annotations 

* `@Benchmark`
  * Methods annotated with `@Benchmark` are the methods which we want to measure.
  * Benchmark method-names are non-essential. (They don't have any significance)
  * Within a same class, you can have muliple `@Benchmark` methods.
  * Note points:
    * If the Benchmark method never finishes, JMH will also never finish.
    * If an Exception is thrown, then the current Benchmark will end abruptly, and next benchmark method will run.
* `@BenchmarkMode`
  * `@BenchmarkMode(Mode.Throughput)` (default)
  * `@BenchmarkMode(Mode.AverageTime)`
  * `@BenchmarkMode(Mode.SampleTime)`
  * `@BenchmarkMode(Mode.SingleShotTime)`
    * for cold start - with no JVM warmups.
  * `@BenchmarkMode(Mode.All)`
* `@OutputTimeUnit(TimeUnit.NANOSECONDS)`
* `@State`
  * The class annotated with `@State` should be `public` (otherwise you will get complie time error).
  * `@Setup` (You can have more than one methods annotated with `@Setup`)
    * `@Setup(Level.Trial)` (default) - The method is called once for each time for each full run of the benchmark. A full run means a full "fork" including all warmup and benchmark iterations.
    * `@Setup(Level.Iteration)` - The method is called once for each iteration of the benchmark.
    * `@Setup(Level.Invocation)` - The method is called once for each call to the benchmark method.      
  * `@Teardown`
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

## Writing good benchmarks

JMH helps you avoid the following points:

* **Dead code elimination**
  * return the result of the computation from the benchmark method.
  * use `Blackhole#consume` so that the JVM don't think that a particular computation is unused and simply ignores it.
    * we generally use blackholes when we can't return multiple things from a single benchmark method. Or you can also combine multiple values into single value and return the result that you got after combining the values.
* **Constant folding**
  * Never hard-code constants into your benchmark methods. 
  * Avoid using local members in benchmark methods. Input to your benchmark should come from the state object.
  * Never declare the members of `@State` class as `final`.
* **Loop Optimizations**
  * JVM is very good at optimizing loops. So you should generally avoid loops inside your benchmark methods (unless they are actually a part of the code that you want to measure).

## Useful links

* [jenkov tutorial](http://tutorials.jenkov.com/java-performance/jmh.html)
* [Sample examples](https://hg.openjdk.java.net/code-tools/jmh/file/1c11c886e0c8/jmh-samples/src/main/java/org/openjdk/jmh/samples/)
