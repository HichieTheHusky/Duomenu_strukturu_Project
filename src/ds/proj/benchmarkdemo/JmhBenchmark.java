/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.benchmarkdemo;

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
public class JmhBenchmark {

    static final int OPERATION_COUNT = 1_000;

    // Parametrai leidžia testą atlikti su skirtingomis konfigūracijomis. Testas
    // bus įvykdytas keletą kartų, kiekvieną sykį listSize užpildant viena iš
    // @Param anotacijoje pateiktų reikšmių. Plačiau žr. JMH pavyzdyje
    // JMHSample_27_Params.java
    @Param({"4000", "8000", "16000", "32000"})
    public int listSize;

    ArrayList<Float> arrayList = new ArrayList<>();
    LinkedList<Float> linkedList = new LinkedList<>();
    int[] indexes = new int[OPERATION_COUNT];

    // Testo būsenos valdymo metodai žymimi @Setup (vykdoma prieš testą) arba
    // @TearDown (vykdoma po testo) anotacijomis. Šių metodų vykdymo laikas
    // nematuojamas. Level parametras nurodo, kada metodai bus vykdomi:
    //   Level.Trial - prieš/po visą testą, t.y. testo iteracijų seką
    //   Level.Iteration - prieš/po kiekvieną iteraciją, t.y. testo metodų iškvietimo seką
    //   Level.Invocation - prieš/po kiekvieną testo metodo iškvietimą
    // Plačiau žr. JMH pavyzdžiuose JMHSample_05_StateFixtures.java ir
    // JMHSample_06_FixtureLevel.java
    @Setup(Level.Trial)
    public void generateLists() {
        util.generateList(arrayList, listSize);
        util.generateList(linkedList, listSize);
    }

    @Setup(Level.Iteration)
    public void generateIndexes() {
        util.generateIndexes(indexes, listSize);
    }

    // @Benchmark anotacija žymi metodus, kurių vykdymo laikas yra matuojamas.
    // JMH pagal anotacijas sugeneruoja pagalbinį kodą, leidžianti greitaveikos
    // matavimus atlikti kuo patikimiau. Plačiau žr. JMH pavyzdyje
    // JMHSample_01_HelloWorld.java
    @Benchmark
    public void arrayListGet() {
        listGet(arrayList);
    }

    @Benchmark
    public void linkedListGet() {
        listGet(linkedList);
    }

    private void listGet(List<Float> list) {
        for (int i : indexes) {
            list.get(i);
        }
    }

    // Kodas, kurio rezultatai nepanaudojami, optimizavimo metu gali būti
    // pašalintas (pvz. metodo List.get(int) iškvietimas). Siekiant to išvengti,
    // galima rezultatus perduoti juos "panaudojantiems" Blackhole objektams.
    // Plačiau žr. JMH pavyzdžiuose JMHSample_08_DeadCode.java ir 
    // JMHSample_09_Blackholes.java
    @Benchmark
    public void arrayListGetAndConsume(Blackhole bh) {
        listGetAndConsume(arrayList, bh);
    }

    @Benchmark
    public void linkedListGetAndConsume(Blackhole bh) {
        listGetAndConsume(linkedList, bh);
    }

    private void listGetAndConsume(List<Float> list, Blackhole bh) {
        for (int i : indexes) {
            bh.consume(list.get(i));
        }
    }

    // Rekomenduojamas JMH testų paleidimo būdas, leidžiantis išvengti Java IDE
    // įtakos testo rezultatams - naudoti testo jar failą:
    //   > java -jar target/benchmarks.jar
    // Tačiau laboratorinių darbų metu testų vykdymui patogiau naudoti JMH
    // Runner ir tiesiog įvykdyti testo klasę.
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
