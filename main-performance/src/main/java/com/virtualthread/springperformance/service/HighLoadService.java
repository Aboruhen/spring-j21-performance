package com.virtualthread.springperformance.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class HighLoadService {

    private final ExecutorService myExecutor;

    public HighLoadService() {
        this.myExecutor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public String calculateScore() {
        System.out.println("Is virtual: " + Thread.currentThread().isVirtual());
        Random random = new Random();
        return Stream.generate(random::nextInt).limit(10_000_000)
            .map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add).toPlainString();
    }

    public void fileProcess() {
        System.out.println("Is virtual: " + Thread.currentThread().isVirtual());
        Path path = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
            .resolve("./../../");
        Path testFilePath = path.resolve("Test.txt");

        try {
            Path path1 = path.resolve("target").resolve("new-file.txt");
            Scanner scanner = new Scanner(new FileInputStream(testFilePath.toFile()));
            List<String> newLines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                newLines.add(scanner.nextLine() + Thread.currentThread());
            }
            Files.write(path1, Stream.generate(() -> "New test line").limit(1000).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String vTcalculationWithSleep() {
        Thread.ofVirtual().start(this::calculationWithSleep);
        try {
            Future<String> submit = myExecutor.submit(this::calculationWithSleep);
            return submit.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException e) {
            throw new ServerErrorException("Error", e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public String calculationWithSleep() {
        System.out.println("Is virtual: " + Thread.currentThread().isVirtual());
        Random random = new Random();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<BigDecimal> decimals = Stream.generate(random::nextInt).limit(100_000)
            .map(BigDecimal::valueOf).toList();
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal decimal : decimals) {
            result = result.add(decimal);
        }
        return result.toPlainString();
    }

}
