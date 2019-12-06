package com.leaderboard.provider.service.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import com.leaderboard.provider.model.LeaderBoardPlayer;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leaderboard.provider.persistence.repository.LeaderBoardUserRepository;
import com.leaderboard.provider.service.LeaderBoardUserService;
import com.leaderboard.provider.util.TestAssert;
import com.leaderboard.provider.util.TestUtil;

public class LeaderBoardLeaderBoardPlayerServiceImplTest {
    @Mock
    private LeaderBoardUserRepository leaderBoardUserRepository;

    private String countryIsoCode;

    private LeaderBoardUserService sut;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new LeaderBoardUserServiceImpl(leaderBoardUserRepository);
        countryIsoCode = TestUtil.getRandomCountry();
    }

    @Test
    public void should_get_empty_global_users_if_no_user_exists() {
        // Given
        when(leaderBoardUserRepository.getUsers()).thenReturn(Collections.emptyList());

        // When
        List<LeaderBoardPlayer> globalLeaderBoardPlayers = sut.getGlobalUsers();

        // Then
        assertTrue(globalLeaderBoardPlayers.isEmpty());
    }

    @Test
    public void should_get_global_users_according_to_descending_points() {
        // Given
        when(leaderBoardUserRepository.getUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getGlobalUsers();

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getUserPoints(leaderBoardPlayers));
    }

    @Test
    public void should_get_global_users_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardUserRepository.getUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getGlobalUsers();

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getUserRanks(leaderBoardPlayers));
    }

    @Test
    public void should_get_empty_country_users_if_no_user_exists() {
        // Given
        when(leaderBoardUserRepository.getUsers()).thenReturn(Collections.emptyList());

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getCountryUsers(countryIsoCode);

        // Then
        assertTrue(leaderBoardPlayers.isEmpty());
    }

    @Test
    public void should_users_have_same_country_when_getting_country_users() {
        // Given
        when(leaderBoardUserRepository.getUsersByCountry(countryIsoCode))
                        .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getCountryUsers(countryIsoCode);

        // Then
        TestAssert.assertUsersAreInSameCountry(countryIsoCode, leaderBoardPlayers);
    }

    @Test
    public void should_get_country_users_according_to_descending_points() {
        // Given
        when(leaderBoardUserRepository.getUsersByCountry(countryIsoCode))
                .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getCountryUsers(countryIsoCode);

        // Then
        TestAssert.assertPointsAreDescending(TestUtil.getUserPoints(leaderBoardPlayers));
    }

    @Test
    public void should_get_country_users_according_to_ascending_ranks_by_one() {
        // Given
        when(leaderBoardUserRepository.getUsersByCountry(countryIsoCode))
                .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));

        // When
        List<LeaderBoardPlayer> leaderBoardPlayers = sut.getCountryUsers(countryIsoCode);

        // Then
        TestAssert.assertRanksAreAscendingByOne(TestUtil.getUserRanks(leaderBoardPlayers));
    }
}
