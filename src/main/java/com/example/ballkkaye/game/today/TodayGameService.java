package com.example.ballkkaye.game.today;

import com.example.ballkkaye.common.enums.GameStatus;
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

@RequiredArgsConstructor
@Service
public class TodayGameService {

    private final TodayGameRepository todayGameRepository;
    private final GameRepository gameRepository;
    private final TodayTeamRecordRepository todayTeamRecordRepository;
    private final StadiumCorrectionRepository stadiumCorrectionRepository;
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;

    @Transactional

    public void updateTodayGames() {

        LocalDate today = LocalDate.now();

        List<Game> todayGames = gameRepository.todayGame(today);


        Double avgOps = todayTeamRecordRepository.getLeagueAverageOps();

        Double totalR = todayTeamRecordRepository.getLeagueAverageR();

        Double avgGames = todayTeamRecordRepository.getAverageGameCount();

        Double avgR = totalR / avgGames;

        Double avgEra = todayTeamRecordRepository.getLeagueAverageEra();


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


            String code = game.getGameCode();

            GameStatus status = game.getGameStatus();


            if ((code == null || code.trim().isEmpty()) &&

                    (status == GameStatus.CANCELLED || status == GameStatus.COMPLETED)) {

                continue;

            }


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


            TodayGameRequest.Save dto = new TodayGameRequest.Save();

            dto.setStadium(game.getStadium());

            dto.setHomeTeam(home);

            dto.setAwayTeam(away);

            dto.setGameTime(game.getGameTime());

            dto.setGameStatus(game.getGameStatus());

            dto.setBroadcastChannel(game.getBroadcastChannel());

            dto.setHomePredictionScore(homeScore);

            dto.setAwayPredictionScore(awayScore);

            dto.setHomeWinPer(homeWinPer);

            dto.setAwayWinPer(awayWinPer);

            dto.setGame(game);


            TodayGame todayGame = dto.toEntity();

            todayGameRepository.saveOrUpdate(todayGame);

        }

    }


}
