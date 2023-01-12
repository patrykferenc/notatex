package com.krabelard.notatex.benchmark.test;

import com.krabelard.notatex.NotatexApplication;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public abstract class BaseSpringPerformanceTest {

    protected ConfigurableApplicationContext context;

    protected void setContext() {
        context = SpringApplication.run(NotatexApplication.class, "--spring.profiles.active=benchmark");
        context.registerShutdownHook();
    }

    protected void closeContext() {
        context.close();
    }
}
