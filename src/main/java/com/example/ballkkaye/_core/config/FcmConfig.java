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

/**
 * 이 클래스는 Spring Boot 애플리케이션에서 Firebase Admin SDK를 초기화하고
 * FirebaseMessaging 인스턴스를 Bean으로 등록하는 설정을 담당합니다.
 * application.properties에 등록된 개별 키 값을 조합하여 유연하게 동작합니다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FcmConfig {

    // 1. application.properties의 firebase 속성을 담고 있는 객체를 주입받습니다.
    private final FirebaseProperties firebaseProperties;
    // 2. 자바 객체를 JSON 문자열로 변환하기 위해 Spring이 관리하는 ObjectMapper를 주입받습니다.
    private final ObjectMapper objectMapper;

    /**
     * application.properties의 속성 값을 사용하여 FirebaseApp을 초기화하고 Bean으로 등록하는 메소드.
     *
     * @return 초기화된 FirebaseApp 인스턴스
     * @throws IOException 설정 파일 로드 중 발생할 수 있는 예외
     */
    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        System.out.println("시작!!!!!!!!!!!!!!!!!!!");

        log.error("❌ error 로그");
        log.warn("⚠️ warn 로그");
        log.info("✅ info 로그");
        log.debug("🐛 debug 로그");
        // 1. properties에서 Base64로 인코딩된 키를 가져옵니다.
        String rawFbPrivateKey = firebaseProperties.getPrivateKey();

        // 2. Base64로 디코딩합니다.
        //    (결과: "-----BEGIN...\\nMIIEvg..." 와 같이 `\\n` 문자가 포함된 문자열)
        String decodedPrivateKeyWithLiterals = Base64Util.decodeBase64(rawFbPrivateKey);

        log.info("rawData :{}", decodedPrivateKeyWithLiterals);

        // 3. [핵심] 디코딩된 문자열에 포함된 `\\n`을 실제 줄 바꿈 문자 `\n`으로 치환합니다.
        String finalFormattedPrivateKey = decodedPrivateKeyWithLiterals.replace("\\n", "\n");

        log.info("originData :{}", finalFormattedPrivateKey);

        // 4. 최종적으로 포맷된 키를 properties 객체에 다시 설정합니다.
        firebaseProperties.setPrivateKey(finalFormattedPrivateKey);

        // 5. 올바른 키가 포함된 객체를 JSON으로 직렬화합니다.
        String json = objectMapper.writeValueAsString(firebaseProperties);

        log.info("json :{}", json);

        // 6. 생성된 JSON 문자열로부터 스트림을 만들어 Firebase를 초기화합니다.
        InputStream serviceAccountStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        try (InputStream serviceAccount = serviceAccountStream) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // 5. 앱이 이미 초기화되었는지 확인하여 중복 초기화를 방지합니다.
            if (FirebaseApp.getApps().isEmpty()) {
                log.debug("파이어베이스 초기화 성공");
                return FirebaseApp.initializeApp(options);
            } else {
                log.debug("파이어베이스 초기화 성공");
                return FirebaseApp.getInstance();
            }
        }
    }

    /**
     * FirebaseMessaging 인스턴스를 Bean으로 등록하는 메소드.
     *
     * @param firebaseApp 위에서 초기화되고 등록된 FirebaseApp Bean을 주입받습니다.
     * @return FirebaseMessaging 인스턴스
     */
    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}