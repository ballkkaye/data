package com.example.ballkkaye._core.config;

import com.example.ballkkaye._core.error.ex.Exception400;
import com.example.ballkkaye._core.error.ex.Exception404;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FcmConfig {

    // Firebase Admin SDK를 초기화
    @PostConstruct
    public void initFirebase() {
        try {
            // [1] 환경변수에서 키 파일 경로 읽기
            String path = System.getenv("FIREBASE_CONFIG_PATH");
            if (path == null || path.isBlank()) {
                throw new Exception404("FIREBASE_CONFIG_PATH 환경변수가 설정되지 않았습니다.");
            }

            // [2] 외부 파일 경로에서 Firebase 서비스 계정 키 파일 로딩
            try (FileInputStream serviceAccount = new FileInputStream(path)) {

                // [3] Firebase 인증 옵션 구성
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                // [4] FirebaseApp이 이미 초기화되지 않은 경우에만 초기화
                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                    log.info("FirebaseApp 초기화 성공 (경로: {})", path);
                } else {
                    log.info("FirebaseApp 이미 초기화되어 있음 - 중복 초기화 생략");
                }
            }

        } catch (IOException e) {
            // [5] 파일이 없거나 JSON 파싱 실패 등
            throw new Exception400("FCM 초기화 실패: " + e.getMessage());
        }
    }
}