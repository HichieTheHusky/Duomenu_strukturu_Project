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
public class JmhBenchmark_removeIndex {

    static final int OPERATION_COUNT = 1_000;

    @Param({"4000", "8000", "16000", "32000"})
    public int listSize;

    UnrolledLinkedList<Float> ULL = new UnrolledLinkedList<>();
    LinkedList<Float> linkedList = new LinkedList<>();

    float[] ULLTest = new float[32000];
    float[] listTest = new float[32000];
    int[] indexes = new int[OPERATION_COUNT];

    @Setup(Level.Invocation)
    public void generateLists() {
        util.generateList(ULL, listSize, ULLTest);
        util.generateList(linkedList, listSize, listTest);
    }

    @Setup(Level.Iteration)
    public void generateIndexes() {
        util.generateIndexes(indexes, listSize);
    }

    @Benchmark
    public void unrolledLinkedListFromIndexRemove(Blackhole bh) {

        for (int i : indexes) {
            bh.consume(ULL.remove(i));
        }
    }

    @Benchmark
    public void linkedListFromListIndexRemove(Blackhole bh) {

        for (int i : indexes) {
            bh.consume(linkedList.remove(i));
        }

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchmark_removeIndex.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
