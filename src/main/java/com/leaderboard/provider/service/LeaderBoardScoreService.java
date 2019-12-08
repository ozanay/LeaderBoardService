package com.leaderboard.provider.service;

import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;

import java.util.List;

/**
 * This service is responsible to provide leaderboard user operations.
 */
public interface LeaderBoardScoreService {
    /**
     * @return Ordered global leaderboard users.
     */
    List<LeaderBoardScore> getGlobalScores();

    /**
     *
     * @param county
     *            According to ISO 3166-1 alpha-2 standards.
     * @return Ordered country leaderboard users.
     */
    List<LeaderBoardScore> getCountryScores(String county);

    /**
     * Submits scores.
     * 
     * @param scores
     *            List of {@link Score}
     * @return submitted list of scores.
     */
    List<Score> submit(List<Score> scores);

    /**
     * Submits single score.
     *
     * @param score
     *            {@link Score}
     * @return submitted score.
     */
    Score submit(Score score);

    /**
     * Adds given {@link ScoreWorth#getValue()} to existent score value for given user.
     * 
     * @param scoreWorth
     *            {@link ScoreWorth}
     * 
     * @return scoreWorth {@link ScoreWorth} with cumulative score value.
     */
    ScoreWorth addScore(ScoreWorth scoreWorth);
}
