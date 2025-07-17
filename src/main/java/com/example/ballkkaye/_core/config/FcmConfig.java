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

        // 1. properties에서 Base64로 인코딩된 키를 가져옵니다.
        String rawFbPrivateKey = """
                LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tXG5NSUlFdlFJQkFEQU5CZ2txaGtpRzl3MEJBUUVGQUFTQ0JLY3dnZ1NqQWdFQUFvSUJBUUNHdXJkOGg1RWl4QWhoXG5zSDVNTHNMYmR0Z2cwVllkZUlHSklXbXluSTVJVWVzOTUxdXlGZ1hUVlBrQ0RTblpTTHUrMWsyaUE4TUFVa08vXG53S0VKcEF0ZFNLVlEwZWFzczBZWm1rNDhsdFlLU3RVTEUwdXRPNUU2VFJsVytkblVMbXo4eWh2UXk3aTdaWnA5XG5HNThxQkxkMStBb3hQcFRVb0dJRHhFSDJ1ZFVNWjZ4bnd1K2dYU1NkWkRvQkRJeHA2YzN1K25pMk9iRHFIejI4XG5Rd3Zyc05vbkdHanN0MXI0Q0pQdkhaOWF3dFlGcjQ3VEE1ZG9nRzhDd0dMeGNuK1pOK3FscUsra3pwZ3EzejgwXG4zQWlYT1ljUFNiZkFDRWNIZkI5MEhpcFRrbXJTK3FlRDl3MnNQRWhiSHdzaDZ4eEdsaU1GUlR5bU85Q2VQYkI4XG5RMGoyUzZ1TkFnTUJBQUVDZ2dFQUtjQzNtRm9TK21reUI4UkFmNitHSjZrKzlYeXFhVnV0R1NoSHIxaWhKM3hyXG5vTFdRVHpKUC85dzNoeEU1Uk9NcmU4ckNLRkZ1SHkxZlhZOSsySlFFQ1phMzFNSVg4TEZtS2cwTjdlejR6QnZBXG54anZtbWZyNTJNMkg2dEpuVlVCRitsbFFJWGJnK3dKVHpDM0JONjVhaEs2bnlmY1EvSXZ2bENkQUlnd1pJRXlWXG5tTThYb0JCL2FIMDJiUk9laGJZSUpHRTl5U01kTFlsQVhwWnJQa1VQVGVHTEF5K2tBZGxyUWd1a3I1dUJYZDZYXG54QmFSMFkzaUtQci9MVXlhRWJvUkF3T2p2eC9Qa2VLbnZVdVBTMmsvSTM2UUNia1A4anR3emN3WlEydGhmaXpkXG4zUVpHVkJHWm1vR3M0TWJ6dlFGTlUxb0J4aUtYSjJwSzlCcVlHczVscVFLQmdRQzlxZk9tZU42L3pmMEl1elJPXG44bC9EcFdWenlTWVEzYStSOHBmZjdUd3lSWVR5Y1luZ3Fzc0dFVFNRR2NsUDI3MStHaFRPZzdkZDF6aTlMbUg1XG5qUHB6N3RISFo5K1BLclJjSVdSb3hlTExIZzNLWFBOSDcxcTlYenlYdk9aUFUrdFN6ME1nbFVuTDlvVFJaSE9rXG4zRmJQM09nbUpVOFNKdTBhQi9nMWw4Skkxd0tCZ1FDMTJnK0RjdDRybjRYY1VHR3ZuM2EwMzQ5TGJuMVpUWjgzXG5obE1rckxMQVpkMFFCWVNQaU1xOHZOTVJZWDRDKzVZVTBxckw2dEJ1aU1Pck5CU1hIRDhLNStOenFTbWpUM0tqXG5KVi9yank3U0t1UTBlVG9pUlNMN3hFME5MKys3ZXRoc3ROb3pza21jL0xsa1Zvb1c4Sk1LWlVDY3lxSE9UQ05vXG5kN1BFWHZ2dU93S0JnQk0wWGtPQkFhMFNRb2pieTlRaW5xZkUyNXo1eFN6NEJZc1ZzV012Tm1Vd3ZPRi90YUpXXG5LUENZVldZeDlqeHIxNmdwSStvMVZCL2daeEpnMi9MVTM1ZFZtMUJCcUZWcTBaQnlvU1FxeFdnanh1bXl0NytVXG44Tzh6OFlLNzBlWStiUm1aZXdhVmx6OWIydDhJK055OG90MnhWS0JvTHJWNzA0NUxPRXJVN3d0SkFvR0FKS2huXG5kaTBDb2pLUERtRlp2OGhhYW8yaVdpZFV0MXJSWEtVRVA2RmNpekk5aE14SDg1TTBveVZCT3dDM0lTek1EVGlHXG5XNExqTDFUOGRKZVVDdjRUcTBRaGNxQklGM2FkZWt1L2NVRzB3NE1INjVnQVl3WWl6ak85QUlxSVBmZStodk5TXG5CUmZNaFF2ZzUxd1dqQmRFM05TYVdNRDBLeHpvQ2xnTzhlZmZ5eDhDZ1lFQWh3cUw2SWVsVmlFeWJqZlZqbXFiXG5wb3BYUmxpTjNhQVFEditGWE9NU3VvVG1DU21PYkl4YzVIdUZYVlF1M29SZ0lwUC9SUmk3aHllMlBFbkU3MzZuXG5DZTNoVlB6SUFaekxraDFUZHBoaG1GNXdnY1hoaytWTGMwYVpIOVZIbDRiUHFmZ1NqMkh0Sys3ajFJQUl2aEErXG5kcVQ3STZuaVJjelhaeXRIaUVDa245TT1cbi0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS1cbg==
                """;

        // 2. Base64로 디코딩합니다.
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