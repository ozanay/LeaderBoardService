package com.leaderboard.provider.service.impl;

import static org.testng.Assert.assertTrue;

public class LeaderBoardLeaderBoardPlayerServiceImplTest {
//    @Mock
//    private LeaderBoardUserRepository leaderBoardUserRepository;
//
//    private String countryIsoCode;
//
//    private LeaderBoardScoreService sut;
//
//    @BeforeMethod
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        sut = new LeaderBoardScoreServiceImpl(leaderBoardUserRepository);
//        countryIsoCode = TestUtil.getRandomCountry();
//    }
//
//    @Test
//    public void should_get_empty_global_users_if_no_user_exists() {
//        // Given
//        when(leaderBoardUserRepository.getScores()).thenReturn(Collections.emptyList());
//
//        // When
//        List<LeaderBoardScore> globalLeaderBoardPlayers = sut.getGlobalScores();
//
//        // Then
//        assertTrue(globalLeaderBoardPlayers.isEmpty());
//    }
//
//    @Test
//    public void should_get_global_users_according_to_descending_points() {
//        // Given
////        when(leaderBoardUserRepository.getScores()).thenReturn(TestUtil.getOrderedUsers());
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getGlobalScores();
//
//        // Then
//        TestAssert.assertPointsAreDescending(TestUtil.getUserPoints(leaderBoardPlayers));
//    }
//
//    @Test
//    public void should_get_global_users_according_to_ascending_ranks_by_one() {
//        // Given
////        when(leaderBoardUserRepository.getScores()).thenReturn(TestUtil.getOrderedUsers());
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getGlobalScores();
//
//        // Then
//        TestAssert.assertRanksAreAscendingByOne(TestUtil.getUserRanks(leaderBoardPlayers));
//    }
//
//    @Test
//    public void should_get_empty_country_users_if_no_user_exists() {
//        // Given
//        when(leaderBoardUserRepository.getScores()).thenReturn(Collections.emptyList());
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getCountryScores(countryIsoCode);
//
//        // Then
//        assertTrue(leaderBoardPlayers.isEmpty());
//    }
//
//    @Test
//    public void should_users_have_same_country_when_getting_country_users() {
//        // Given
////        when(leaderBoardUserRepository.getScoresByCountry(countryIsoCode))
////                        .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getCountryScores(countryIsoCode);
//
//        // Then
//        TestAssert.assertUsersAreInSameCountry(countryIsoCode, leaderBoardPlayers);
//    }
//
//    @Test
//    public void should_get_country_users_according_to_descending_points() {
//        // Given
////        when(leaderBoardUserRepository.getScoresByCountry(countryIsoCode))
////                .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getCountryScores(countryIsoCode);
//
//        // Then
//        TestAssert.assertPointsAreDescending(TestUtil.getUserPoints(leaderBoardPlayers));
//    }
//
//    @Test
//    public void should_get_country_users_according_to_ascending_ranks_by_one() {
//        // Given
////        when(leaderBoardUserRepository.getScoresByCountry(countryIsoCode))
////                .thenReturn(TestUtil.getUsersWithAscendingAnyRanks(countryIsoCode));
//
//        // When
//        List<LeaderBoardScore> leaderBoardPlayers = sut.getCountryScores(countryIsoCode);
//
//        // Then
//        TestAssert.assertRanksAreAscendingByOne(TestUtil.getUserRanks(leaderBoardPlayers));
//    }
}
