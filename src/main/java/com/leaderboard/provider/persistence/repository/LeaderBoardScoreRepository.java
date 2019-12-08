package com.leaderboard.provider.persistence.repository;

import java.util.List;

import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;

public interface LeaderBoardScoreRepository {
    List<LeaderBoardScore> getScores();

    List<LeaderBoardScore> getScoresByCountry(String countryIsoCode);

    List<Score> submit(List<Score> scores);

    Score submit(Score score);

    Score getScoreByUserId(String userId);

    Score save(Score score);
}
