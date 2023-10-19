package com.virtualthread.springperformance.rest;

import com.virtualthread.springperformance.service.HighLoadService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import main.java.com.virtualthread.springperformance.config.ConfigServer;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

@ImportAutoConfiguration(ConfigServer.class)
@RestController
public class PerformanceController {

    private final HighLoadService highLoadService;

    public PerformanceController(HighLoadService highLoadService) {
        this.highLoadService = highLoadService;
    }

    @GetMapping("performance")
    String performanceTestExecute() {
        return highLoadService.calculateScore();
    }

    @GetMapping("files-performance")
    void performanceFilesTestExecute() {
        highLoadService.fileProcess();
    }

    @GetMapping("vt-performance")
    String performanceWithVirtualThreadsTestExecute() {
        try (ExecutorService myExecutor =
            Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> submit = myExecutor.submit(highLoadService::calculateScore);
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ServerErrorException("Error", e);
        }
    }

    @GetMapping("vt-files-performance")
    void performanceWithVirtualThreadsFilesTestExecute() {
        Thread start = Thread.ofVirtual().start(highLoadService::fileProcess);
        try {
            start.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("performance-sleep")
    String performanceTestWithSleep() {
        return highLoadService.calculationWithSleep();
    }

    @GetMapping("vt-performance-sleep")
    String performanceWithVirtualThreadsWithSleep() {
       return highLoadService.vTcalculationWithSleep();
    }

}
