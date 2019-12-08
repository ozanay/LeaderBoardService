package com.leaderboard.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.exception.ResourceNotFoundException;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import com.leaderboard.provider.persistence.repository.LeaderBoardScoreRepository;
import com.leaderboard.provider.service.LeaderBoardScoreService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LeaderBoardScoreServiceImpl implements LeaderBoardScoreService {
    private LeaderBoardScoreRepository leaderBoardScoreRepository;
    private LeaderBoardConversionService conversionService;

    @Override
    public List<LeaderBoardScore> getGlobalScores() {
        log.info("Getting global scores...");
        List<LeaderBoardScore> leaderBoardScores = leaderBoardScoreRepository.getScores();
        updateRanks(leaderBoardScores);
        log.info("Getting global scores finished successfully.");
        return leaderBoardScores;
    }

    @Override
    public List<LeaderBoardScore> getCountryScores(String county) {
        log.info("Getting country scores...");
        List<LeaderBoardScore> leaderBoardScores = leaderBoardScoreRepository.getScoresByCountry(county);
        updateRanks(leaderBoardScores);
        log.info("Getting country scores finished successfully.");
        return leaderBoardScores;
    }

    @Override
    public List<Score> submit(List<Score> scores) {
        log.info("Submitting scores...");
        List<Score> submittedScores = leaderBoardScoreRepository.submit(scores);
        log.info("Submitting scores finished successfully.");
        return submittedScores;
    }

    @Override
    public Score submit(Score score) {
        log.info("Submitting single score...");
        Score submittedScores = leaderBoardScoreRepository.submit(score);
        log.info("Submitting single score finished successfully.");
        return submittedScores;
    }

    @Override
    public ScoreWorth addScore(ScoreWorth scoreWorth) {
        log.info("Adding score value <{}> for user with id <{}>...", scoreWorth.getValue(), scoreWorth.getUserId());
        Score score = leaderBoardScoreRepository.getScoreByUserId(scoreWorth.getUserId());
        if (score == null) {
            throw new ResourceNotFoundException(String.format("Given user <%s> is not found.", scoreWorth.getUserId()));
        }

        double newScore = score.getScoreValue() + scoreWorth.getValue();
        score.setScoreValue(newScore);
        Score savedScore = leaderBoardScoreRepository.save(score);
        ScoreWorth savedScoreWorth = conversionService.convert(savedScore, ScoreWorth.class);
        log.info("Adding score value <{}> for user with id <{}> finished successfully.", scoreWorth.getValue(),
                        scoreWorth.getUserId());
        return savedScoreWorth;
    }

    private void updateRanks(List<LeaderBoardScore> leaderBoardScores) {
        for (int i = 0; i < leaderBoardScores.size(); i++) {
            LeaderBoardScore leaderBoardScore = leaderBoardScores.get(i);
            leaderBoardScore.setRank(i + 1);
        }
    }
}
