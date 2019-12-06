package com.leaderboard.provider.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leaderboard.provider.controller.resource.LeaderBoardPlayerResource;
import com.leaderboard.provider.service.LeaderBoardUserService;
import com.leaderboard.provider.util.TestAssert;
import com.leaderboard.provider.util.TestUtil;

public class LeaderBoardControllerTest {
    @Mock
    private LeaderBoardUserService leaderBoardUserService;

    private LeaderBoardController sut;
    private String countryIsoCode;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        countryIsoCode = TestUtil.getRandomCountry();
        sut = new LeaderBoardController(leaderBoardUserService, TestUtil.conversionService());
    }

    @Test
    public void should_get_empty_global_leaderboard_if_no_user_exists() {
        // Given
        when(leaderBoardUserService.getGlobalUsers()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getGlobalLeaderBoard();

        // Then
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void should_get_global_leaderboard_according_to_descending_points() {
        // Given
        when(leaderBoardUserService.getGlobalUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getGlobalLeaderBoard();

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardResourcePoints(response.getBody()));
    }

    @Test
    public void should_get_global_leaderboard_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardUserService.getGlobalUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getGlobalLeaderBoard();

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardResourceRanks(response.getBody()));
    }

    @Test
    public void should_return_bad_request_response_if_invalid_iso_country_code_is_given() {
        // Given
        String invalidIsoCode = "invalid";

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getCountryLeaderBoard(invalidIsoCode);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void should_get_empty_country_leaderboard_if_no_user_exists() {
        // Given
        when(leaderBoardUserService.getCountryUsers(countryIsoCode)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void should_get_country_leaderboard_according_to_descending_points() {
        // Given
        when(leaderBoardUserService.getCountryUsers(countryIsoCode))
                        .thenReturn(TestUtil.getOrderedUsers(countryIsoCode));

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getLeaderBoardResourcePoints(response.getBody()));
    }

    @Test
    public void should_get_country_leaderboard_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardUserService.getCountryUsers(countryIsoCode))
                        .thenReturn(TestUtil.getOrderedUsers(countryIsoCode));

        // When
        ResponseEntity<List<LeaderBoardPlayerResource>> response = sut.getCountryLeaderBoard(countryIsoCode);

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getLeaderBoardResourceRanks(response.getBody()));
    }
}
