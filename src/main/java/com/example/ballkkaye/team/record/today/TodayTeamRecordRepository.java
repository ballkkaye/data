package com.example.ballkkaye.team.record.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayTeamRecordRepository {
    private final EntityManager em;

    public void deleteAll() {
        em.createQuery("DELETE FROM TodayTeamRecord").executeUpdate();
    }

    public void save(List<TodayTeamRecord> teamRecords) {
        for (TodayTeamRecord teamRecord : teamRecords) {
            em.persist(teamRecord);
        }
    }
}
