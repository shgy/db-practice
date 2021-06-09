package org.example.branch;

import org.example.jmh.JmhHello;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 0)
@Measurement(iterations = 1)
public class BranchPredictionDemo {

    private int[] sortedData;

    private int[] randomData;

    @Setup
    public void setup(BenchmarkParams params) {

        this.randomData =  gendata(32768,false);;
        this.sortedData = gendata(32768,true);

    }

    private static int[] gendata(int size,boolean isSort){
        Random r =new Random();
        int[] data = new int[size];
        for(int i=0;i<size;i++){
            data[i]=r.nextInt() % 256;
        }
        if(isSort){
            Arrays.sort(data);
        }
        return data;
    }

    @Benchmark
    public long sortedArrayTest(){
        long sum = 0;
        for (int i = 0; i < 1; ++i)
        {
            for(int c=0;c<sortedData.length;c++){
                if(sortedData[c]>= 128){
                    sum+=sortedData[c];
                }
            }
        }

        return sum;
    }

    @Benchmark
    public long randomArrayTest(){

        long sum = 0;
        for (int i = 0; i < 1; ++i)
        {
            for(int c=0;c<randomData.length;c++){
                if(randomData[c]>= 128){
                    sum+=randomData[c];
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BranchPredictionDemo.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
