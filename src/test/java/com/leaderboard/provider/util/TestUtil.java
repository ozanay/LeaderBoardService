package com.leaderboard.provider.util;

import com.leaderboard.provider.config.ConverterConfig;
import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardPlayerResource;
import com.leaderboard.provider.model.LeaderBoardPlayer;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TestUtil {
    private static final Random random = new SecureRandom();
    private static final LeaderBoardConversionService conversionService = new ConverterConfig().conversionService();

    private static final String DISPLAY_NAME_PREFIX = "PLAYER_";
    private static final int RANDOM_BOUND = 100;
    private static final int DESCENDING_POINT_RANDOM_BOUND = 10;

    private TestUtil() {
    }

    public static List<LeaderBoardPlayer> getOrderedUsers() {
        LeaderBoardPlayer first = TestUtil.createUser(1, 100);
        LeaderBoardPlayer second = TestUtil.createUser(2, 99);
        LeaderBoardPlayer third = TestUtil.createUser(3, 98);

        return Arrays.asList(first, second, third);
    }

    public static List<LeaderBoardPlayer> getOrderedUsers(String countryIsoCode) {
        LeaderBoardPlayer first = TestUtil.createUser(1, 100, countryIsoCode);
        LeaderBoardPlayer second = TestUtil.createUser(2, 99, countryIsoCode);
        LeaderBoardPlayer third = TestUtil.createUser(3, 98, countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    public static List<LeaderBoardPlayer> getUsersWithAscendingAnyRanks(String countryIsoCode) {
        int firstRank = random.nextInt(RANDOM_BOUND);
        int firstPoints = random.nextInt(RANDOM_BOUND) + RANDOM_BOUND;
        LeaderBoardPlayer first = TestUtil.createUser(firstRank, firstPoints, countryIsoCode);

        int secondRank = nextAscendingRank(firstRank);
        int secondPoints = nextDescendingPoint(firstPoints);
        LeaderBoardPlayer second = TestUtil.createUser(secondRank, secondPoints, countryIsoCode);
        LeaderBoardPlayer third = TestUtil.createUser(nextAscendingRank(secondRank), nextDescendingPoint(secondPoints),
                        countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    private static LeaderBoardPlayer createUser(int rank, int points) {
        return createUser(rank, points, getRandomCountry());
    }

    private static LeaderBoardPlayer createUser(int rank, int points, String countryIsoCode) {
        return LeaderBoardPlayer.builder()
                .id(generateId())
                .rank(rank)
                .points(points)
                .country(countryIsoCode)
                .displayName(generateRandomName())
                .build();
    }

    public static LeaderBoardConversionService conversionService() {
        return conversionService;
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String getRandomCountry() {
        String[] isoCountries = Locale.getISOCountries();
        int countryIndex = random.nextInt(isoCountries.length);
        return isoCountries[countryIndex];
    }

    private static String generateRandomName() {
        return DISPLAY_NAME_PREFIX + generateId();
    }

    public static List<Integer> getLeaderBoardResourcePoints(List<LeaderBoardPlayerResource> leaderBoardPlayerResources) {
        return leaderBoardPlayerResources.stream().map(LeaderBoardPlayerResource::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getLeaderBoardResourceRanks(List<LeaderBoardPlayerResource> leaderBoardPlayerResources) {
        return leaderBoardPlayerResources.stream().map(LeaderBoardPlayerResource::getRank).collect(Collectors.toList());
    }

    public static List<Integer> getUserPoints(List<LeaderBoardPlayer> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardPlayer::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getUserRanks(List<LeaderBoardPlayer> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardPlayer::getRank).collect(Collectors.toList());
    }

    private static int nextAscendingRank(int currentRank) {
        return currentRank + random.nextInt(RANDOM_BOUND);
    }

    private static int nextDescendingPoint(int currentPoint) {
        return currentPoint - random.nextInt(DESCENDING_POINT_RANDOM_BOUND);
    }
}
