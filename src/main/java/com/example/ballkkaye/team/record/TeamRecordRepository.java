package com.example.ballkkaye.team.record;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TeamRecordRepository {
    private final EntityManager em;

    public void saveAll(List<TeamRecord> teamRecords) {
        for (TeamRecord teamRecord : teamRecords) {
            em.persist(teamRecord);
        }
    }

    public List<TeamRecord> findLatest10() {
        return em.createQuery(
                        "SELECT tr FROM TeamRecord tr ORDER BY tr.createdAt DESC, tr.teamRank ASC",
                        TeamRecord.class
                )
                .setMaxResults(10)
                .getResultList();
    }
}
