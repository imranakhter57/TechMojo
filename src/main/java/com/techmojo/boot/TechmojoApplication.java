package com.techmojo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.techmojo")
@EnableScheduling
public class TechmojoApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.techmojo.boot.TechmojoApplication.class, args);
    }
}
