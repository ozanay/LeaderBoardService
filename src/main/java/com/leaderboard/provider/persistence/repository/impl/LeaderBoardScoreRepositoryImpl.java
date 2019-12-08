package com.leaderboard.provider.persistence.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.persistence.entity.ScoreEntity;
import com.leaderboard.provider.persistence.repository.LeaderBoardScoreRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LeaderBoardScoreRepositoryImpl implements LeaderBoardScoreRepository {
    private LeaderBoardScoreJpaRepository leaderBoardScoreJpaRepository;
    private LeaderBoardConversionService conversionService;

    @Override
    public List<LeaderBoardScore> getScores() {
        log.info("Getting scores from repo...");
        Iterable<ScoreEntity> scoreEntities = leaderBoardScoreJpaRepository.findByOrderByScoreValueDesc();
        List<LeaderBoardScore> leaderBoardScores = conversionService.convertToList(scoreEntities,
                        LeaderBoardScore.class);
        log.info("Getting scores from repo finished successfully.");
        return leaderBoardScores;
    }

    @Override
    public List<LeaderBoardScore> getScoresByCountry(String country) {
        log.info("Getting scores of country <{}> from repo...", country);
        Sort sort = Sort.by("scoreValue").descending();
        Iterable<ScoreEntity> countryScoreEntities = leaderBoardScoreJpaRepository
                        .findAllByUserEntity_Country(country, sort);
        List<LeaderBoardScore> leaderBoardScores = conversionService.convertToList(countryScoreEntities,
                        LeaderBoardScore.class);
        log.info("Getting scores of country <{}> from repo finished successfully.", country);
        return leaderBoardScores;
    }

    @Override
    public List<Score> submit(List<Score> scores) {
        log.info("Submitting scores to db...");
        List<ScoreEntity> scoreEntitiesToBeSubmitted = conversionService.convertToList(scores, ScoreEntity.class);
        Iterable<ScoreEntity> savedScoreEntities = leaderBoardScoreJpaRepository.saveAll(scoreEntitiesToBeSubmitted);

        List<Score> submittedScores = conversionService.convertToList(savedScoreEntities, Score.class);
        log.info("Submitting scores to db finished successfully.");
        return submittedScores;
    }

    @Override
    public Score submit(Score score) {
        log.info("Submitting single score to db...");
        ScoreEntity scoreEntityToBeSubmitted = conversionService.convert(score, ScoreEntity.class);
        ScoreEntity savedScoreEntity = leaderBoardScoreJpaRepository.save(scoreEntityToBeSubmitted);
        Score submittedScore = conversionService.convert(savedScoreEntity, Score.class);
        log.info("Submitting single score to db finished successfully.");
        return submittedScore;
    }

    @Override
    public Score getScoreByUserId(String userId) {
        log.info("Getting score by user id <{}>", userId);
        ScoreEntity scoreEntity = leaderBoardScoreJpaRepository.findByUserEntity_Id(userId);
        return (scoreEntity == null) ? null : conversionService.convert(scoreEntity, Score.class);
    }

    @Override
    public Score save(Score score) {
        log.info("Saving score by user with id <{}>...", score.getUser().getId());
        ScoreEntity scoreEntity = conversionService.convert(score, ScoreEntity.class);
        ScoreEntity savedEntity = leaderBoardScoreJpaRepository.save(scoreEntity);
        Score savedScore = conversionService.convert(savedEntity, Score.class);
        log.info("Saving score by user with id <{}> finished successfully.", score.getUser().getId());
        return savedScore;
    }
}
