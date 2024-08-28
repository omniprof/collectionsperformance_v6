package com.kenfogel.performance;

//import com.kenfogel.performance.loaders.MapTests_jmh;
import com.kenfogel.performance.loaders.MapTests_jmh;
import com.kenfogel.performance.loaders.SequenceTests_jmh;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

/**
 * Run the JMH tests
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
public class CollectionPerformanceApp_JMH {

    public static void main(String[] args) throws InterruptedException, RunnerException, Exception {
        runBenchmark(SequenceTests_jmh.class);
        runBenchmark(MapTests_jmh.class);
    }

    public static void runBenchmark(Class<?> clazz) throws Exception {
        Options baseOpts = new OptionsBuilder()
                .include(clazz.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .shouldDoGC(true)
                .result(clazz.getSimpleName() + ".json")
                // .output("Results.txt")
                .shouldFailOnError(true)
                .timeUnit(TimeUnit.NANOSECONDS)
                .mode(Mode.AverageTime)
                .verbosity(VerboseMode.EXTRA)
                .forks(1)
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(2L))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(2L))
                .build();

        // Simple run
        //new Runner(baseOpts).run();
        
        // Collect all the results so that they can be accessed programatically.
        // Run the benchmarks and collect the results from each benchmark
        Collection<RunResult> runner = new Runner(baseOpts).run();

        // Iterator to go thru all the results
        Iterator<RunResult> allResults = runner.iterator();
        while (allResults.hasNext()) {
            Result myResult = allResults.next().getPrimaryResult();
            System.out.printf("%s  %10.3f %s%n",myResult.getLabel(),
                    myResult.getScore(), myResult.getScoreUnit()
            );
        }
    }
}
