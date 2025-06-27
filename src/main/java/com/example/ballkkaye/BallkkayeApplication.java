package com.example.ballkkaye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BallkkayeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BallkkayeApplication.class, args);
    }

}
