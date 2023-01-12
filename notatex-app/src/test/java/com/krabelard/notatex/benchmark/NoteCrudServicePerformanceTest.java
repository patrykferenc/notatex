package com.krabelard.notatex.benchmark;

import com.krabelard.notatex.benchmark.test.BaseSpringPerformanceTest;
import com.krabelard.notatex.note.repository.NoteRepository;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Objects;

public class NoteCrudServicePerformanceTest extends BaseSpringPerformanceTest {

    @MockBean
    private NoteRepository REPOSITORY;

    @InjectMocks
    private NoteCrudService SERVICE;

    @Disabled("Disabled as it fails - TODO")
    @Test
    void runBenchmarks() throws RunnerException {
        val runner = new Runner(new OptionsBuilder()
                .include(String.format("\\.%s\\.", NoteCrudServicePerformanceTest.class.getSimpleName()))
                .warmupIterations(2)
                .measurementIterations(10)
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
        for (int i = 0; i < 10; ++i) {
            try (
                    val inputStream = NoteCrudServicePerformanceTest.class.getClassLoader().getResourceAsStream("sample.tex")
            ) {
                val bytes = Objects.requireNonNull(inputStream).readAllBytes();
                val fileName = String.format("sample%d.tex", i);
                val mockFile = new MockMultipartFile(fileName, bytes);
                SERVICE.create(mockFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
