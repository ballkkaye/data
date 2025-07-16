package com.example.ballkkaye._core.config;

import com.example.ballkkaye._core.util.Base64Util;
import com.example.ballkkaye.fcm.FirebaseProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 이 클래스는 Spring Boot 애플리케이션에서 Firebase Admin SDK를 초기화하고
 * FirebaseMessaging 인스턴스를 Bean으로 등록하는 설정을 담당합니다.
 * application.properties에 등록된 개별 키 값을 조합하여 유연하게 동작합니다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FcmConfig {

    private final FirebaseProperties firebaseProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // 1. 환경 변수에서 '깨끗한 Base64' 키를 가져옵니다.
        String base64EncodedKey = firebaseProperties.getPrivateKey();


// ✅ 2. 디코딩 수행
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedKey);
// ✅ 3. 복원된 PEM 키
        String originalPemKey = new String(decodedBytes, StandardCharsets.UTF_8);
        firebaseProperties.setPrivateKey(originalPemKey);

        // 4. JSON으로 변환하여 초기화합니다.
        String json = objectMapper.writeValueAsString(firebaseProperties);
        InputStream serviceAccountStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        try (InputStream serviceAccount = serviceAccountStream) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            } else {
                return FirebaseApp.getInstance();
            }
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}