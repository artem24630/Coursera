package com.example.demo.config;

import com.example.demo.service.StatisticsCounter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsConfig { // это Java Config и из него можно создать несколько бинов
    @Bean
    public StatisticsCounter statisticsCounter() { //   данный метод создает бин
        return new StatisticsCounter();
    }
}