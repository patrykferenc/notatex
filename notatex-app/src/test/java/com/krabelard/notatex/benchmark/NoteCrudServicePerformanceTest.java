package com.krabelard.notatex.benchmark;

import com.krabelard.notatex.benchmark.test.BaseSpringPerformanceTest;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Objects;

public class NoteCrudServicePerformanceTest extends BaseSpringPerformanceTest {

    private NoteCrudService noteCrudService;

    private MockMultipartFile noteFile;

    @SneakyThrows
    @Setup(Level.Invocation)
    public void setupNoteFile() {
        final var inputStream = getClass().getClassLoader().getResourceAsStream("notatex/benchmark/" + "simple.tex");
        final var fileName = RandomStringUtils.randomAlphanumeric(26) + ".tex";
        final var content = Objects.requireNonNull(inputStream).readAllBytes();
        noteFile = new MockMultipartFile(fileName, fileName, MediaType.TEXT_PLAIN_VALUE, content);
    }

    @Setup
    public void setup() {
        setContext();

        noteCrudService = context.getBean(NoteCrudService.class);
    }

    @TearDown
    public void tearDown() {
        closeContext();
    }

    @Test
    void runBenchmarks() throws RunnerException {
        val runner = new Runner(new OptionsBuilder()
                .include(String.format("\\.%s\\.", NoteCrudServicePerformanceTest.class.getSimpleName()))
                .warmupIterations(2)
                .measurementIterations(8)
                .forks(1)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build()
        );

        runner.run();
    }

    @Benchmark
    public void createOkBenchmark(Blackhole blackhole) {
            blackhole.consume(noteCrudService.create(noteFile));
    }
}
