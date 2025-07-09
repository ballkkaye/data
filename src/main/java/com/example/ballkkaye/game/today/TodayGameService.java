package com.example.ballkkaye.game.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.player.startingPitcher.today.TodayStartingPitcherRepository;
import com.example.ballkkaye.stadium.stadiumCorrection.StadiumCorrectionRepository;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.record.today.TodayTeamRecord;
import com.example.ballkkaye.team.record.today.TodayTeamRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodayGameService {

    private final TodayGameRepository todayGameRepository;
    private final GameRepository gameRepository;
    private final TodayTeamRecordRepository todayTeamRecordRepository;
    private final StadiumCorrectionRepository stadiumCorrectionRepository;
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;

    @Transactional
    public void syncTodayGames() {

        LocalDate today = LocalDate.now();

        List<Game> todayGames = gameRepository.findTodayGame(today);
        Double avgOps = todayTeamRecordRepository.leagueAverageOps();
        Double totalR = todayTeamRecordRepository.leagueAverageR();
        Double avgGames = todayTeamRecordRepository.averageGameCount();
        Double avgR = totalR / avgGames;
        Double avgEra = todayTeamRecordRepository.leagueAverageEra();

        Map<Integer, TodayTeamRecord> recordMap = new HashMap<>();

        for (TodayTeamRecord record : todayTeamRecordRepository.findAll()) {
            if (record.getTeam() != null && record.getTeam().getId() != null) {
                recordMap.put(record.getTeam().getId(), record);
            }
        }

        for (Game game : todayGames) {
            Team home = game.getHomeTeam();
            Team away = game.getAwayTeam();
            if (home == null || away == null) continue;

            TodayTeamRecord homeRecord = recordMap.get(home.getId());
            TodayTeamRecord awayRecord = recordMap.get(away.getId());
            if (homeRecord == null || awayRecord == null) continue;
            Integer stadiumId = game.getStadium().getId();
            Double correction = stadiumCorrectionRepository
                    .getCorrectionByStadiumIdAndYear(stadiumId, LocalDate.now(ZoneId.of("Asia/Seoul")).getYear());
            Double homeOps = homeRecord.getOPS();
            Double awayOps = awayRecord.getOPS();
            if (homeOps == null || awayOps == null) continue;
            Double homePitcherEra = todayStartingPitcherRepository.getPitcherEraByGameAndTeam(game, home);
            Double awayPitcherEra = todayStartingPitcherRepository.getPitcherEraByGameAndTeam(game, away);

            if (homePitcherEra == null || homePitcherEra == 0.0) homePitcherEra = avgEra;
            if (awayPitcherEra == null || awayPitcherEra == 0.0) awayPitcherEra = avgEra;
            double rawHomeScore = (homeOps / avgOps) * avgR * (avgEra / awayPitcherEra) * correction;
            double rawAwayScore = (awayOps / avgOps) * avgR * (avgEra / homePitcherEra) * correction;
            double homeScore = Math.round(rawHomeScore * 10) / 10.0;
            double awayScore = Math.round(rawAwayScore * 10) / 10.0;
            double homeWinPer = 50.0;
            double awayWinPer = 50.0;
            double total = homeScore + awayScore;

            if (total > 0) {
                homeWinPer = Math.round((homeScore / total) * 1000) / 10.0;
                awayWinPer = Math.round((awayScore / total) * 1000) / 10.0;
            }

            Optional<TodayGame> existingTodayGameOptional = todayGameRepository.findByGameId(game.getId());

            if (existingTodayGameOptional.isPresent()) {
                // 이미 TodayGame이 존재하는 경우: 경기 상태 및 결과 점수만 업데이트
                TodayGame existingTodayGame = existingTodayGameOptional.get();

                existingTodayGame.update(
                        game.getGameStatus(),
                        game.getHomeResultScore(),
                        game.getAwayResultScore()
                );

                todayGameRepository.update(existingTodayGame);

            } else {

                TodayGame newTodayGame = TodayGame.builder()
                        .game(game)
                        .stadium(game.getStadium())
                        .homeTeam(home)
                        .awayTeam(away)
                        .gameTime(game.getGameTime())
                        .gameStatus(game.getGameStatus())
                        .broadcastChannel(game.getBroadcastChannel())
                        .homePredictionScore(homeScore)
                        .awayPredictionScore(awayScore)
                        .totalPredictionScore(total)
                        .homeWinPer(homeWinPer)
                        .awayWinPer(awayWinPer)
                        .homeResultScore(game.getHomeResultScore())
                        .awayResultScore(game.getAwayResultScore())
                        .build();

                todayGameRepository.save(newTodayGame);
            }
        }
    }
}