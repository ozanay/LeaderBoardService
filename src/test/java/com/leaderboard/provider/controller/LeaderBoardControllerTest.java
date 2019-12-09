package com.leaderboard.provider.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.ScoreWorthResource;
import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.exception.BadRequestException;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.service.LeaderBoardScoreService;
import com.leaderboard.provider.util.TestAssert;
import com.leaderboard.provider.util.TestUtil;

public class LeaderBoardControllerTest {
    @Mock
    private LeaderBoardScoreService leaderBoardScoreService;

    private LeaderBoardConversionService conversionService = TestUtil.conversionService();
    private LeaderBoardController sut;
    private String countryIsoCode;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        countryIsoCode = TestUtil.getRandomCountry();
        sut = new LeaderBoardController(leaderBoardScoreService, TestUtil.conversionService());
    }

    @Test
    public void should_get_empty_global_leaderboard_if_no_user_exists() {
        // Given
        when(leaderBoardScoreService.getGlobalScores()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getGlobalLeaderBoard();

        // Then
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void should_get_global_leaderboard_according_to_descending_points() {
        // Given
        when(leaderBoardScoreService.getGlobalScores()).thenReturn(TestUtil.getOrderedUsers());

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getGlobalLeaderBoard();

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardResourcePoints(response.getBody()));
    }

    @Test
    public void should_get_global_leaderboard_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardScoreService.getGlobalScores()).thenReturn(TestUtil.getOrderedUsers());

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getGlobalLeaderBoard();

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardResourceRanks(response.getBody()));
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void should_throw_bad_request_exception_if_invalid_iso_country_code_is_given() {
        // Given
        String invalidIsoCode = "invalid";

        // When
        sut.getCountryLeaderBoard(invalidIsoCode);
    }

    @Test
    public void should_get_empty_country_leaderboard_if_no_user_exists() {
        // Given
        when(leaderBoardScoreService.getCountryScores(countryIsoCode)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void should_get_country_leaderboard_according_to_descending_points() {
        // Given
        when(leaderBoardScoreService.getCountryScores(countryIsoCode))
                        .thenReturn(TestUtil.getOrderedUsers(countryIsoCode));

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardResourcePoints(response.getBody()));
    }

    @Test
    public void should_get_country_leaderboard_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardScoreService.getCountryScores(countryIsoCode))
                        .thenReturn(TestUtil.getOrderedUsers(countryIsoCode));

        // When
        ResponseEntity<List<LeaderBoardResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardResourceRanks(response.getBody()));
    }

    @Test
    public void should_return_submitted_score_worth_for_given_user() {
        // Given
        ScoreWorthResource scoreWorthResource = TestUtil.createRandomScoreWorthResource();
        ScoreWorth scoreWorth = conversionService.convert(scoreWorthResource, ScoreWorth.class);

        when(leaderBoardScoreService.addScore(any(ScoreWorth.class))).thenReturn(scoreWorth);

        // When
        ResponseEntity<ScoreWorthResource> response = sut.submitScore(scoreWorthResource);

        // Then
        assertEquals(response.getBody().getUserId(), scoreWorthResource.getUserId());
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void should_throw_bad_request_exception_when_submitting_empty_list_of_users() {
        // Given
        List<UserResource> emptyUsers = Collections.emptyList();

        // When
        sut.submit(emptyUsers);
    }

    @Test
    public void should_return_submitted_user_list_successfully() {
        // Given
        List<UserResource> userResources = TestUtil.createRandomUserResourceList(5);
        List<Score> scores = conversionService.convertToList(userResources, Score.class);
        when(leaderBoardScoreService.submit(anyList())).thenReturn(scores);

        // When
        ResponseEntity<List<UserResource>> response = sut.submit(userResources);

        // Then
        TestAssert.assertBothListsContainSameUsers(response.getBody(), userResources);
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void should_throw_bad_Request_exception_when_submitting_single_user_with_invalid_country() {
        // Given
        UserResource userWithInvalidCountry = TestUtil.createRandomUserResource();
        userWithInvalidCountry.setCountry("invalid");

        // When
        sut.submit(userWithInvalidCountry);
    }

    @Test
    public void should_return_submitted_user_successfully() {
        // Given
        UserResource userResource = TestUtil.createRandomUserResource();
        Score score = conversionService.convert(userResource, Score.class);
        when(leaderBoardScoreService.submit(any(Score.class))).thenReturn(score);

        // When
        ResponseEntity<UserResource> response = sut.submit(userResource);

        // Then
        assertEquals(response.getBody().getId(), userResource.getId());
    }
}
