package com.krabelard.notatex.benchmark;

import com.krabelard.notatex.note.repository.NoteRepository;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {NoteCrudService.class, NoteH2Config.class})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class NoteCrudServiceBenchmark {

    @MockBean
    private NoteRepository REPOSITORY;
    @InjectMocks
    private NoteCrudService SERVICE;

    @Test
    void runBenchmarks() throws RunnerException {
        val runner = new Runner(new OptionsBuilder()
                .include(String.format("\\.%s\\.", NoteCrudServiceBenchmark.class.getSimpleName()))
                .warmupIterations(10)
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
                    val inputStream = NoteCrudServiceBenchmark.class.getClassLoader().getResourceAsStream("sample.tex")
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
