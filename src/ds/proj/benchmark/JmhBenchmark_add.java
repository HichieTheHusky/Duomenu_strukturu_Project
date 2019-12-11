/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.benchmark;

import ds.proj.util.UnrolledLinkedList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)

@State(Scope.Benchmark)

@OutputTimeUnit(TimeUnit.MICROSECONDS)

@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
/**
 *
 * @author husky
 */
public class JmhBenchmark_add {

    static final int OPERATION_COUNT = 1_000;

    @Param({"4000", "8000", "16000", "32000"})
    public int listSize;

    UnrolledLinkedList<Float> ULL = new UnrolledLinkedList<>();
    LinkedList<Float> linkedList = new LinkedList<>();

    float[] ULLTest = new float[32000];
    float[] listTest = new float[32000];

    @Benchmark
    public void unrolledLinkedListObjectAdd(Blackhole bh) {
        ULL = new UnrolledLinkedList<>();
        util.generateList(ULL, listSize, ULLTest);

    }

    @Benchmark
    public void linkedListObjectAdd(Blackhole bh) {
        linkedList = new LinkedList<>();
        util.generateList(linkedList, listSize, listTest);

    }

    // Rekomenduojamas JMH testų paleidimo būdas, leidžiantis išvengti Java IDE
    // įtakos testo rezultatams - naudoti testo jar failą:
    //   > java -jar target/benchmarks.jar
    // Tačiau laboratorinių darbų metu testų vykdymui patogiau naudoti JMH
    // Runner ir tiesiog įvykdyti testo klasę.
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchmark_add.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
