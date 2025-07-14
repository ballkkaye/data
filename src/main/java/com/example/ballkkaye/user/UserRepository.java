package com.example.ballkkaye.user;

import com.example.ballkkaye.common.enums.UserRole;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    // UserRole.USER 이고 fcmToken이 null이 아닌 사용자들의 FCM 토큰 목록 조회
    public List<String> findFcmTokensByRole(UserRole role) {
        return em.createQuery("""
                            SELECT u.fcmToken
                            FROM User u
                            WHERE u.userRole = :role
                              AND u.fcmToken IS NOT NULL
                              AND u.fcmToken <> ''
                        """, String.class)
                .setParameter("role", role)
                .getResultList();
    }
}
