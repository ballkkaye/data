package com.example.ballkkaye;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BallkkayeApplication {

    public static void main(String[] args) {
        // .env 파일 로드 및 시스템 프로퍼티 설정
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed() // .env 파일 형식이 잘못되어도 무시
                .ignoreIfMissing()   // .env 파일이 없어도 오류 무시
                .load();

        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));


        SpringApplication.run(BallkkayeApplication.class, args);
    }

}
