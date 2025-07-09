package com.example.ballkkaye.user.userPrediction;

import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.common.enums.PredictionStatus;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserPredictionService {
    private final UserPredictionRepository userPredictionRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void evaluatePredictions() {
        // COMPLETED + CANCELLED 상태 모두 조회
        List<Game> evaluatedGames = gameRepository.findByGameStatusIn(
                List.of(GameStatus.COMPLETED, GameStatus.CANCELLED)
        );

        for (Game game : evaluatedGames) {
            // 1. 취소된 경기는 무조건 무승부
            if (game.getGameStatus() == GameStatus.CANCELLED) {
                List<UserPrediction> predictions = userPredictionRepository.findByGame(game);
                for (UserPrediction prediction : predictions) {
                    if (prediction.getResult() == PredictionStatus.WAITING) {
                        prediction.updateResult(PredictionStatus.TIE);
                    }
                }
                continue;
            }

            // 2. 완료된 경기는 점수 비교
            Integer homeScore = game.getHomeResultScore();
            Integer awayScore = game.getAwayResultScore();
            if (homeScore == null || awayScore == null) continue;

            Team winner = null;
            if (homeScore > awayScore) {
                winner = game.getHomeTeam();
            } else if (awayScore > homeScore) {
                winner = game.getAwayTeam();
            }

            List<UserPrediction> predictions = userPredictionRepository.findByGame(game);
            for (UserPrediction prediction : predictions) {
                if (prediction.getResult() != PredictionStatus.WAITING) continue;

                PredictionStatus result;
                if (winner == null) {
                    result = PredictionStatus.TIE;
                } else if (prediction.getUserChoiceTeam().getId().equals(winner.getId())) {
                    result = PredictionStatus.CORRECT;
                } else {
                    result = PredictionStatus.INCORRECT;
                }

                prediction.updateResult(result);
            }
        }
    }
}
