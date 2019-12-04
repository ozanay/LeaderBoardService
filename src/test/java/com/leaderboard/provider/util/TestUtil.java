package com.leaderboard.provider.util;

import com.leaderboard.provider.config.ConverterConfig;
import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardResource;
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

    private static final String DISPLAY_NAME_PREFIX = "PLAYER_";
    private static final int RANDOM_BOUND = 100;
    private static final int DESCENDING_POINT_RANDOM_BOUND = 10;

    private TestUtil() {
    }

    public static List<User> getOrderedUsers() {
        User first = TestUtil.createUser(1, 100);
        User second = TestUtil.createUser(2, 99);
        User third = TestUtil.createUser(3, 98);

        return Arrays.asList(first, second, third);
    }

    public static List<User> getOrderedUsers(String countryIsoCode) {
        User first = TestUtil.createUser(1, 100, countryIsoCode);
        User second = TestUtil.createUser(2, 99, countryIsoCode);
        User third = TestUtil.createUser(3, 98, countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    public static List<User> getUsersWithAscendingAnyRanks(String countryIsoCode) {
        int firstRank = random.nextInt(RANDOM_BOUND);
        int firstPoints = random.nextInt(RANDOM_BOUND) + RANDOM_BOUND;
        User first = TestUtil.createUser(firstRank, firstPoints, countryIsoCode);

        int secondRank = nextAscendingRank(firstRank);
        int secondPoints = nextDescendingPoint(firstPoints);
        User second = TestUtil.createUser(secondRank, secondPoints, countryIsoCode);
        User third = TestUtil.createUser(nextAscendingRank(secondRank), nextDescendingPoint(secondPoints),
                        countryIsoCode);

        return Arrays.asList(first, second, third);
    }

    private static User createUser(int rank, int points) {
        return createUser(rank, points, getRandomCountry());
    }

    private static User createUser(int rank, int points, String countryIsoCode) {
        return User.builder()
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

    public static List<Integer> getLeaderBoardResourcePoints(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getLeaderBoardResourceRanks(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getRank).collect(Collectors.toList());
    }

    public static List<Integer> getUserPoints(List<User> leaderBoardResources) {
        return leaderBoardResources.stream().map(User::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getUserRanks(List<User> leaderBoardResources) {
        return leaderBoardResources.stream().map(User::getRank).collect(Collectors.toList());
    }

    private static int nextAscendingRank(int currentRank) {
        return currentRank + random.nextInt(RANDOM_BOUND);
    }

    private static int nextDescendingPoint(int currentPoint) {
        return currentPoint - random.nextInt(DESCENDING_POINT_RANDOM_BOUND);
    }
}
