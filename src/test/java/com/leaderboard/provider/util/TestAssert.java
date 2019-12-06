package com.leaderboard.provider.util;

import com.leaderboard.provider.model.LeaderBoardPlayer;

import java.util.List;

import static org.testng.Assert.*;

public final class TestAssert {
    private TestAssert() {}

    public static void assertPointsAreDescending(List<Integer> userPoints) {
        for (int i = 0; i < userPoints.size(); i++) {
            int nextIndex = i + 1;
            if (nextIndex != userPoints.size()) {
                int current = userPoints.get(i);
                int next = userPoints.get(nextIndex);
                assertTrue(current > next);
            }
        }
    }

    public static void assertRanksAreAscendingByOne(List<Integer> userRanks) {
        for (int i = 0; i < userRanks.size(); i++) {
            int expectedRank = i + 1;
            assertEquals(userRanks.get(i).intValue(), expectedRank);
        }
    }

    public static void assertUsersAreInSameCountry(String countryIsoCode, List<LeaderBoardPlayer> leaderBoardPlayers) {
        assertFalse(leaderBoardPlayers.isEmpty());
        boolean areAllSame = leaderBoardPlayers.stream().map(LeaderBoardPlayer::getCountry).allMatch(countryIsoCode::equals);
        assertTrue(areAllSame);
    }
}
