package com.example.ballkkaye.game.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TodayGameRepository {

    private final EntityManager em;

}
