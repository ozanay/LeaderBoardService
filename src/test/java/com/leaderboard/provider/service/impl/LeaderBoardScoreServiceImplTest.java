package com.leaderboard.provider.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import com.leaderboard.provider.exception.ResourceNotFoundException;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.persistence.repository.LeaderBoardScoreRepository;
import com.leaderboard.provider.util.TestAssert;
import com.leaderboard.provider.util.TestUtil;

public class LeaderBoardScoreServiceImplTest {
    @Mock
    private LeaderBoardScoreRepository leaderBoardScoreRepository;

    private LeaderBoardConversionService conversionService = TestUtil.conversionService();
    private LeaderBoardScoreServiceImpl sut;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new LeaderBoardScoreServiceImpl(leaderBoardScoreRepository, conversionService);
    }

    @Test
    public void should_return_empty_global_scores_if_no_score_exists() {
        // Given
        when(leaderBoardScoreRepository.getScores()).thenReturn(Collections.emptyList());

        // When
        List<LeaderBoardScore> globalScores = sut.getGlobalScores();

        // Then
        assertTrue(globalScores.isEmpty());
    }

    @Test
    public void should_return_global_scores_by_rank_by_sorted_ascending() {
        // Given
        when(leaderBoardScoreRepository.getScores()).thenReturn(TestUtil.createSortedLeaderBoardScoresWithoutRanks(5));

        // When
        List<LeaderBoardScore> globalScores = sut.getGlobalScores();

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardScoreRanks(globalScores));
    }

    @Test
    public void should_return_global_scores_by_points_by_sorted_descending() {
        // Given
        when(leaderBoardScoreRepository.getScores()).thenReturn(TestUtil.createSortedLeaderBoardScoresWithoutRanks(5));

        // When
        List<LeaderBoardScore> globalScores = sut.getGlobalScores();

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardScorePoints(globalScores));
    }

    @Test
    public void should_return_empty_country_scores_if_no_score_exists() {
        // Given
        String country = TestUtil.getRandomCountry();
        when(leaderBoardScoreRepository.getScoresByCountry(country)).thenReturn(Collections.emptyList());

        // When
        List<LeaderBoardScore> countryScores = sut.getCountryScores(country);

        // Then
        assertTrue(countryScores.isEmpty());
    }

    @Test
    public void should_return_country_scores_by_rank_by_sorted_ascending() {
        // Given
        String country = TestUtil.getRandomCountry();
        when(leaderBoardScoreRepository.getScoresByCountry(country))
                        .thenReturn(TestUtil.createSortedLeaderBoardScoresWithoutRanks(5, country));

        // When
        List<LeaderBoardScore> countryScores = sut.getCountryScores(country);

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardScoreRanks(countryScores));
    }

    @Test
    public void should_return_country_scores_by_points_by_sorted_descending() {
        // Given
        String country = TestUtil.getRandomCountry();
        when(leaderBoardScoreRepository.getScoresByCountry(country))
                .thenReturn(TestUtil.createSortedLeaderBoardScoresWithoutRanks(5, country));

        // When
        List<LeaderBoardScore> countryScores = sut.getCountryScores(country);

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardScorePoints(countryScores));
    }

    @Test
    public void should_return_scores_in_the_same_country() {
        // Given
        String country = TestUtil.getRandomCountry();
        when(leaderBoardScoreRepository.getScoresByCountry(country))
                .thenReturn(TestUtil.createSortedLeaderBoardScoresWithoutRanks(5, country));

        // When
        List<LeaderBoardScore> countryScores = sut.getCountryScores(country);

        // Then
        List<String> countries = TestUtil.getLeaderBoardScoresCountries(countryScores);
        TestAssert.assertScoresInTheSameCountry(country, countries);
    }

    @Test
    public void should_return_submitted_score_successfully() {
        // Given
        Score score = TestUtil.createRandomScore();
        when(leaderBoardScoreRepository.submit(any(Score.class))).thenReturn(score);

        // When
        Score submittedScore = sut.submit(score);

        // Then
        TestAssert.assertSame(submittedScore, score);
    }

    @Test
    public void should_return_submitted_score_list_successfully() {
        // Given
        List<Score> scores = TestUtil.createRandomScoreList(5);
        when(leaderBoardScoreRepository.submit(anyList())).thenReturn(scores);

        // When
        List<Score> submittedScores = sut.submit(scores);

        // Then
        TestAssert.assertSameList(submittedScores, scores);
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_throw_resource_not_found_exception_if_no_user_exists_for_given_user() {
        // Given
        ScoreWorth scoreWorth = TestUtil.createRandomScoreWorth();
        when(leaderBoardScoreRepository.getScoreByUserId(scoreWorth.getUserId())).thenReturn(null);

        // When
        sut.addScore(scoreWorth);
    }

    @Test
    public void should_return_cumulative_score_value_when_adding_score() {
        // Given
        Score score = TestUtil.createRandomScore();
        String userId = score.getUser().getId();
        ScoreWorth scoreWorth = TestUtil.createScoreWorthWithId(userId);
        double cumulativeScoreValue = score.getScoreValue() + scoreWorth.getValue();

        when(leaderBoardScoreRepository.getScoreByUserId(userId)).thenReturn(score);
        when(leaderBoardScoreRepository.save(any())).thenReturn(score);

        // When
        ScoreWorth submittedScore = sut.addScore(scoreWorth);

        // Then

        assertEquals(submittedScore.getValue(), cumulativeScoreValue);
    }
}
