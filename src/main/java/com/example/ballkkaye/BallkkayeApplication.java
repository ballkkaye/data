package com.example.ballkkaye;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class BallkkayeApplication {

    public static void main(String[] args) {
        // 1. 먼저 .env 로드
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        // 2. .env 내용 Spring 기본 프로퍼티로 등록
        Map<String, Object> props = new HashMap<>();
        dotenv.entries().forEach(entry -> props.put(entry.getKey(), entry.getValue()));

        // 3. SpringApplication에 주입
        SpringApplication app = new SpringApplication(BallkkayeApplication.class);
        app.setDefaultProperties(props);

        // 4. 실행
        app.run(args);
    }
}


