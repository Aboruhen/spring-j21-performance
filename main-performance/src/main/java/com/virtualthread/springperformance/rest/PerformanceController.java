package com.virtualthread.springperformance.rest;

import com.virtualthread.springperformance.service.HighLoadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
