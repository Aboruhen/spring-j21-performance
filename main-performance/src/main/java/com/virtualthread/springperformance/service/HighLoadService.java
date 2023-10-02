package com.virtualthread.springperformance.service;

import org.springframework.stereotype.Service;

@Service
public class HighLoadService {

    public String calculateScore() {
        return "100000";
    }

}
