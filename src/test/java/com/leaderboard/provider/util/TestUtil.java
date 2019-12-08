package com.leaderboard.provider.util;

import com.leaderboard.provider.config.ConverterConfig;
import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.User;

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

    private static final String DISPLAY_NAME_PREFIX = "USER_";
    private static final int RANDOM_BOUND = 100;
    private static final int DESCENDING_POINT_RANDOM_BOUND = 10;

    private TestUtil() {
    }

    public static List<LeaderBoardScore> getOrderedUsers() {
        LeaderBoardScore first = TestUtil.createLeaderBoardScore(1, 100);
        LeaderBoardScore second = TestUtil.createLeaderBoardScore(2, 99);
        LeaderBoardScore third = TestUtil.createLeaderBoardScore(3, 98);

        return Arrays.asList(first, second, third);
    }

    public static List<LeaderBoardScore> getOrderedUsers(String countryIsoCode) {
        LeaderBoardScore first = TestUtil.createLeaderBoardScore(1, 100, countryIsoCode);
        LeaderBoardScore second = TestUtil.createLeaderBoardScore(2, 99, countryIsoCode);
        LeaderBoardScore third = TestUtil.createLeaderBoardScore(3, 98, countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    public static List<LeaderBoardScore> getUsersWithAscendingAnyRanks(String countryIsoCode) {
        int firstRank = random.nextInt(RANDOM_BOUND);
        int firstPoints = random.nextInt(RANDOM_BOUND) + RANDOM_BOUND;
        LeaderBoardScore first = TestUtil.createLeaderBoardScore(firstRank, firstPoints, countryIsoCode);

        int secondRank = nextAscendingRank(firstRank);
        int secondPoints = nextDescendingPoint(firstPoints);
        LeaderBoardScore second = TestUtil.createLeaderBoardScore(secondRank, secondPoints, countryIsoCode);
        LeaderBoardScore third = TestUtil.createLeaderBoardScore(nextAscendingRank(secondRank), nextDescendingPoint(secondPoints),
                        countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    private static LeaderBoardScore createLeaderBoardScore(int rank, int points) {
        return createLeaderBoardScore(rank, points, getRandomCountry());
    }

    private static LeaderBoardScore createLeaderBoardScore(int rank, int points, String countryIsoCode) {
        User user = User.builder()
                .id(generateId())
                .country(countryIsoCode)
                .displayName(generateRandomName())
                .name(generateRandomName())
                .surname(generateRandomName())
                .build();
        return LeaderBoardScore.leaderBoardScoreBuilder()
                .id(generateId())
                .rank(rank)
                .scoreValue(points)
                .user(user)
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

    public static List<Integer> getLeaderBoardResourcePoints(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getLeaderBoardResourceRanks(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getRank).collect(Collectors.toList());
    }

    public static List<Integer> getUserPoints(List<LeaderBoardScore> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardScore::getScoreValue).collect(Collectors.toList());
    }

    public static List<Integer> getUserRanks(List<LeaderBoardScore> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardScore::getRank).collect(Collectors.toList());
    }

    private static int nextAscendingRank(int currentRank) {
        return currentRank + random.nextInt(RANDOM_BOUND);
    }

    private static int nextDescendingPoint(int currentPoint) {
        return currentPoint - random.nextInt(DESCENDING_POINT_RANDOM_BOUND);
    }
}
