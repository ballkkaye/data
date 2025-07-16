package com.example.ballkkaye._core.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.sentry.Sentry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Configuration
public class FcmConfig {

    private static final String CONFIG_DIR = "config";
    private static final String FIREBASE_KEY_FILE = "firebase-key.json";

    @PostConstruct
    public void initFirebase() {
        try {
            // [1] 환경변수에서 Base64 인코딩된 Firebase 키 가져오기
            String base64 = System.getenv("FIREBASE_CONFIG_BASE64");
            if (base64 == null || base64.isBlank()) {
                String msg = "FIREBASE_CONFIG_BASE64 환경변수가 설정되지 않았습니다.";
                log.error(msg);
                Sentry.captureMessage(msg);
                throw new IllegalStateException(msg);
            }

            // [2] config 디렉토리 생성
            Path configDir = Paths.get(CONFIG_DIR);
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

            // [3] 디코딩하여 config/firebase-key.json 로 저장
            Path firebaseKeyPath = configDir.resolve(FIREBASE_KEY_FILE);
            byte[] decoded = Base64.getDecoder().decode(base64);
            Files.write(firebaseKeyPath, decoded);

            // [4] Firebase 초기화
            try (FileInputStream serviceAccount = new FileInputStream(firebaseKeyPath.toFile())) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                    log.info("FirebaseApp 초기화 성공 (경로: {})", firebaseKeyPath);
                } else {
                    log.info("FirebaseApp 이미 초기화되어 있음 - 중복 초기화 생략");
                }
            }

        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("FCM 초기화 실패: {}", e.getMessage(), e);
        }
    }
}