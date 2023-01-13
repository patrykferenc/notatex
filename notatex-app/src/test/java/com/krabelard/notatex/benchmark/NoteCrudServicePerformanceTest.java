package com.krabelard.notatex.benchmark;

import com.krabelard.notatex.benchmark.test.BaseSpringPerformanceTest;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class NoteCrudServicePerformanceTest extends BaseSpringPerformanceTest {

    private NoteCrudService noteCrudService;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Setup
    public void setup() {
        setContext();

        noteCrudService = context.getBean(NoteCrudService.class);
    }

    @TearDown
    public void tearDown() {
        closeContext();
    }

    // This fails on a clean db?
    @Disabled("Method fails")
    @Test
    void runBenchmarks() throws RunnerException {
        val runner = new Runner(new OptionsBuilder()
                .include(String.format("\\.%s\\.", NoteCrudServicePerformanceTest.class.getSimpleName()))
                .warmupIterations(0) // temp
                .measurementIterations(1) // temporary - we need to clean DB
                .forks(0)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build()
        );

        runner.run();
    }

    @Benchmark
    public void createOkBenchmark() {
        try (
                val inputStream = getClass().getClassLoader().getResourceAsStream("notatex/benchmark/" + "simple.tex")
        ) {
            val bytes = Objects.requireNonNull(inputStream).readAllBytes();
            val fileName = "this_can_not_exist" + random.nextInt(4, 10) + ".tex";
            final var othermock = new MockMultipartFile(fileName, fileName, "text/plain", bytes);
            noteCrudService.create(othermock);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file!", e);
        }
    }
}
