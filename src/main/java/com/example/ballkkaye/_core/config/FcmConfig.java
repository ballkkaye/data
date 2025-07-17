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
<<<<<<< HEAD

        System.out.println("시작!!!!!!!!!!!!!!!!!!!");
        // 1. properties에서 Base64로 인코딩된 키를 가져옵니다.
        String rawFbPrivateKey = firebaseProperties.getPrivateKey();

        // 2. Base64로 디코딩합니다.
        //    (결과: "-----BEGIN...\\nMIIEvg..." 와 같이 `\\n` 문자가 포함된 문자열)
        String decodedPrivateKeyWithLiterals = Base64Util.decodeBase64(rawFbPrivateKey);

        log.debug("decodedPrivateKeyWithLiterals :{}", decodedPrivateKeyWithLiterals);
        System.out.println("decodedPrivateKeyWithLiterals :" + decodedPrivateKeyWithLiterals);

        // 3. [핵심] 디코딩된 문자열에 포함된 `\\n`을 실제 줄 바꿈 문자 `\n`으로 치환합니다.
        String finalFormattedPrivateKey = decodedPrivateKeyWithLiterals.replace("\\n", "\n");
=======
        // 1. 환경 변수에서 '깨끗한 Base64' 키를 가져옵니다.
        String base64EncodedKey = firebaseProperties.getPrivateKey();
>>>>>>> ffbb0fdb5a4a5f42a9a02e9940b465c14be34613

        log.debug("finalFormattedPrivateKey :{}", finalFormattedPrivateKey);
        System.out.println("finalFormattedPrivateKey :" + finalFormattedPrivateKey);

        log.debug("파이어베이스 key {}", finalFormattedPrivateKey);

// ✅ 2. 디코딩 수행
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedKey);
// ✅ 3. 복원된 PEM 키
        String originalPemKey = new String(decodedBytes, StandardCharsets.UTF_8);
        firebaseProperties.setPrivateKey(originalPemKey);

        // 4. JSON으로 변환하여 초기화합니다.
        String json = objectMapper.writeValueAsString(firebaseProperties);
<<<<<<< HEAD

        log.debug("json :{}", json);
        System.out.println("json :" + json);


        // 6. 생성된 JSON 문자열로부터 스트림을 만들어 Firebase를 초기화합니다.
=======
>>>>>>> ffbb0fdb5a4a5f42a9a02e9940b465c14be34613
        InputStream serviceAccountStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        try (InputStream serviceAccount = serviceAccountStream) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                log.debug("파이어베이스 초기화 성공");
                return FirebaseApp.initializeApp(options);
            } else {
                log.debug("파이어베이스 초기화 성공");
                return FirebaseApp.getInstance();
            }
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}