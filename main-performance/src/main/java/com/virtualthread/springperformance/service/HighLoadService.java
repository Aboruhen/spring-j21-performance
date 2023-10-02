package com.virtualthread.springperformance.service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class HighLoadService {

    public String calculateScore() {
        Random random = new Random();
        return Stream.generate(random::nextInt).limit(10_000_000)
            .map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add).toPlainString();
    }

}
