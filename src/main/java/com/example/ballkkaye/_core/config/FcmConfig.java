package com.example.ballkkaye._core.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Slf4j
@Configuration
public class FcmConfig {

    @Value("${FIREBASE_CONFIG_BASE64}") // application.properties에 설정한 키 사용
    private String firebaseConfigBase64;

    @PostConstruct
    public void initFirebase() {
        try {
            // [1] 환경변수에서 Base64 인코딩된 Firebase 키 가져오기
            if (firebaseConfigBase64 == null || firebaseConfigBase64.isBlank()) {
                String msg = "FIREBASE_CONFIG_BASE64 환경변수가 설정되지 않았습니다.";
                log.error(msg);
                throw new IllegalStateException(msg);
            }

            // [3] 디코딩하여 config/firebase-key.json 로 저장
            byte[] decoded = Base64.getDecoder().decode(firebaseConfigBase64);

            // [4] Firebase 초기화
            try {
                // JSON 문자열로 Firebase 인증 정보를 생성
                String json = new String(decoded);
                GoogleCredentials credentials = GoogleCredentials.fromStream(
                        new java.io.ByteArrayInputStream(json.getBytes())
                );

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                } else {
                    log.info("FirebaseApp 이미 초기화되어 있음 - 중복 초기화 생략");
                }

            } catch (Exception e) {
                log.error("Firebase 초기화 실패: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            log.error("FCM 초기화 실패: {}", e.getMessage(), e);
        }
    }
}