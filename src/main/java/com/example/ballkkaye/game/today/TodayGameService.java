package com.example.ballkkaye.game.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.game.today.TodayGameRequest.SaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayGameService {

    private final TodayGameRepository todayGameRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void updateTodayGames() {
        // 1. 오늘 날짜 기준 전체 경기 조회
        LocalDate today = LocalDate.now();
        List<Game> todayGames = gameRepository.todayGame(today);

        // 2. 기존 today_game_tb 데이터 초기화
        todayGameRepository.deleteAll();

        // 3. Game → TodayGameRequest.SaveRequest → TodayGame 변환
        List<TodayGame> todayGameList = new ArrayList<>();
        for (Game game : todayGames) {
            SaveRequest dto = new SaveRequest();
            dto.setStadium(game.getStadium());
            dto.setHomeTeam(game.getHomeTeam());
            dto.setAwayTeam(game.getAwayTeam());
            dto.setGameTime(game.getGameTime());
            dto.setGameStatus(game.getGameStatus());
            dto.setHomeResultScore(game.getHomeResultScore());
            dto.setAwayResultScore(game.getAwayResultScore());
            dto.setBroadcastChannel(game.getBroadcastChannel());
            dto.setHomeWinPer(game.getHomeWinPer());
            dto.setAwayWinPer(game.getAwayWinPer());

            TodayGame todayGame = dto.toEntity();
            todayGameList.add(todayGame);
        }

        // 4. 저장
        todayGameRepository.save(todayGameList);
    }
}
