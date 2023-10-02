package com.virtualthread.springperformance.service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class HighLoadService {

    public String calculateScore() {
        Random random = new Random();
        try (ExecutorService myExecutor =
            Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> submit = myExecutor.submit(() -> Stream.generate(random::nextInt).limit(10_000_000)
                .map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add).toPlainString());
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ServerErrorException("Error", e);
        }
    }

}
