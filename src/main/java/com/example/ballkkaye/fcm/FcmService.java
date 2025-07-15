package com.example.ballkkaye.fcm;

import com.example.ballkkaye.common.enums.UserRole;
import com.example.ballkkaye.user.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {

    private final UserRepository userRepository;


    // 일반 사용자(USER 권한)를 대상으로 FCM 푸시 알림 전송
    public void sendToUserRoleUsers(String messageBody) {
        // [1] USER 역할을 가진 사용자의 FCM 토큰 리스트 조회
        List<String> userFcmTokens = userRepository.findFcmTokensByRole(UserRole.USER);

        // [2] 공통 알림 내용 구성
        Notification notification = Notification.builder()
                .setTitle("ballkkaye") // 알림 제목
                .setBody(messageBody)  // 알림 본문
                .build();


        // [3] 각 사용자에게 개별 메시지 전송
        for (String token : userFcmTokens) {
            if (token == null || token.isBlank()) continue;  // 유효하지 않은 토큰은 건너뜀

            // [3-1] 개별 대상에게 보낼 메시지 구성
            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(token) // 특정 디바이스를 식별하는 FCM 토큰
                    .build();


            // [3-2] 메시지 전송 시도
            try {
                String response = FirebaseMessaging.getInstance().send(message);
                log.info("FCM 전송 완료: {}", response);
            } catch (FirebaseMessagingException e) {
                Sentry.captureException(e);
                log.error("FCM 전송 실패 ({}): {}", token, e.getMessage());
            }
        }
    }

}
