package com.example.ballkkaye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BallkkayeApplication {

    public static void main(String[] args) {
        // 3. SpringApplication에 주입
        SpringApplication app = new SpringApplication(BallkkayeApplication.class);
        // 4. 실행
        app.run(args);
    }
}


