package com.javaetmoi.benchmark;

import com.javaetmoi.benchmark.mapping.mapper.bull.BullMapper;
import com.javaetmoi.benchmark.mapping.mapper.datus.DatusMapper;
import com.javaetmoi.benchmark.mapping.mapper.dozer.DozerMapper;
import com.javaetmoi.benchmark.mapping.mapper.jmapper.JMapperMapper;
import com.javaetmoi.benchmark.mapping.mapper.manual.ManualMapper;
import com.javaetmoi.benchmark.mapping.mapper.mapstruct.MapStructMapper;
import com.javaetmoi.benchmark.mapping.mapper.modelmapper.ModelMapper;
import com.javaetmoi.benchmark.mapping.mapper.orika.OrikaMapper;
import com.javaetmoi.benchmark.mapping.mapper.remappe.ReMappeMapper;
import com.javaetmoi.benchmark.mapping.mapper.selma.SelmaMapper;
import com.javaetmoi.benchmark.mapping.model.dto.OrderDTO;
import com.javaetmoi.benchmark.mapping.model.entity.Order;
import com.javaetmoi.benchmark.mapping.model.entity.OrderFactory;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

@State(Scope.Thread)
public class MapperBenchmark {
    private Order order;

    @Setup(Level.Iteration)
    public void preInit() {
        order = OrderFactory.buildOrder();
    }

    @Benchmark
    public OrderDTO DozerMapperBenchmark() { return new DozerMapper().map(order); }

    @Benchmark
    public OrderDTO OrikaMapperBenchmark() { return new OrikaMapper().map(order); }

    @Benchmark
    public OrderDTO ModelMapperBenchmark() { return new ModelMapper().map(order); }
    @Benchmark
    public OrderDTO MapStructMapperBenchmark() { return new MapStructMapper().map(order); }
    @Benchmark
    public OrderDTO SelmaMapperBenchmark() { return new SelmaMapper().map(order); }
    @Benchmark
    public OrderDTO JMapperMapperBenchmark() { return new JMapperMapper().map(order); }
    @Benchmark
    public OrderDTO ManualMapperBenchmark() { return new ManualMapper().map(order); }
    @Benchmark
    public OrderDTO BullMapperBenchmark() { return new BullMapper().map(order); }
    @Benchmark
    public OrderDTO DatusMapperBenchmark() { return new DatusMapper().map(order); }
    @Benchmark
    public OrderDTO ReMappeMapperBenchmark() { return new ReMappeMapper().map(order); }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder()
            .include(MapperBenchmark.class.getSimpleName())
            .warmupIterations(5)
            .measurementIterations(5)
            .jvmArgs("-server")
            .forks(1)
            .resultFormat(ResultFormatType.TEXT)
            .build();

        Collection<RunResult> results = new Runner(opts).run();
        for (RunResult result : results) {
            Result<?> r = result.getPrimaryResult();
            System.out.println("API replied benchmark score: "
                + r.getScore() + " "
                + r.getScoreUnit() + " over "
                + r.getStatistics().getN() + " iterations");
        }
    }

}
