package com.leaderboard.provider.util;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

import com.leaderboard.provider.config.ConverterConfig;
import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.controller.resource.ScoreWorthResource;
import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import com.leaderboard.provider.model.User;

public final class TestUtil {
    private static final Random random = new SecureRandom();
    private static final LeaderBoardConversionService conversionService = new ConverterConfig().conversionService();

    private static final String NAME_PREFIX = "USER_NAME_";
    private static final String SURNAME_PREFIX = "USER_SURNAME_";
    private static final String DISPLAY_NAME_PREFIX = "USER_DISPLAY_NAME_";
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

    private static LeaderBoardScore createLeaderBoardScoreWithoutRank(int points) {
        return createLeaderBoardScoreWithoutRank(points, getRandomCountry());
    }

    private static LeaderBoardScore createLeaderBoardScore(int rank, int points) {
        return createLeaderBoardScore(rank, points, getRandomCountry());
    }

    private static LeaderBoardScore createLeaderBoardScore(int rank, int points, String countryIsoCode) {
        LeaderBoardScore leaderBoardScore = createLeaderBoardScoreWithoutRank(points, countryIsoCode);
        leaderBoardScore.setRank(rank);
        return  leaderBoardScore;
    }

    private static LeaderBoardScore createLeaderBoardScoreWithoutRank(int points, String countryIsoCode) {
        User user = User.builder()
                .id(generateId())
                .country(countryIsoCode)
                .displayName(generateRandomName())
                .name(generateRandomName())
                .surname(generateRandomName())
                .build();
        return LeaderBoardScore.leaderBoardScoreBuilder()
                .id(generateId())
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
        return NAME_PREFIX + generateId();
    }

    private static String generateRandomSurname() {
        return SURNAME_PREFIX + generateId();
    }

    private static String generateRandomDisplayName() {
        return DISPLAY_NAME_PREFIX + generateId();
    }

    private static double generateRandomScoreValue() {
        return random.nextInt(RANDOM_BOUND) + random.nextDouble();
    }

    public static List<Double> getLeaderBoardResourcePoints(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getPoints).collect(Collectors.toList());
    }

    public static List<Integer> getLeaderBoardResourceRanks(List<LeaderBoardResource> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardResource::getRank).collect(Collectors.toList());
    }

    public static List<Double> getLeaderBoardScorePoints(List<LeaderBoardScore> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardScore::getScoreValue).collect(Collectors.toList());
    }

    public static List<Integer> getLeaderBoardScoreRanks(List<LeaderBoardScore> leaderBoardResources) {
        return leaderBoardResources.stream().map(LeaderBoardScore::getRank).collect(Collectors.toList());
    }

    private static int nextDescendingPoint(int currentPoint) {
        return currentPoint - random.nextInt(DESCENDING_POINT_RANDOM_BOUND);
    }

    public static ScoreWorthResource createRandomScoreWorthResource() {
        double score = generateRandomScoreValue();
        return ScoreWorthResource.builder()
                .timestamp(System.currentTimeMillis())
                .userId(generateId())
                .scoreWorth(score)
                .build();
    }

    public static ScoreWorth createRandomScoreWorth() {
        double score = generateRandomScoreValue();
        return ScoreWorth.builder()
                .timestamp(System.currentTimeMillis())
                .userId(generateId())
                .value(score)
                .build();
    }

    public static ScoreWorth createScoreWorthWithId(String userId) {
        double score = generateRandomScoreValue();
        return ScoreWorth.builder()
                .timestamp(System.currentTimeMillis())
                .userId(userId)
                .value(score)
                .build();
    }

    public static List<UserResource> createRandomUserResourceList(int size) {
        List<UserResource> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UserResource resource = createRandomUserResource();
            users.add(resource);
        }

        return users;
    }

    public static UserResource createRandomUserResource() {
        return UserResource.builder()
                .id(generateId())
                .name(generateRandomName())
                .surname(generateRandomSurname())
                .displayName(generateRandomDisplayName())
                .country(getRandomCountry())
                .build();
    }

    public static List<LeaderBoardScore> createSortedLeaderBoardScoresWithoutRanks(int size) {
        List<LeaderBoardScore> leaderBoardScores = new ArrayList<>();
        int previousPoints = random.nextInt(RANDOM_BOUND) + RANDOM_BOUND;
        for (int i = 0; i <size; i++) {
            int nextPoints = nextDescendingPoint(previousPoints);
            LeaderBoardScore leaderBoardScore = createLeaderBoardScoreWithoutRank(nextPoints);
            leaderBoardScores.add(leaderBoardScore);
            previousPoints = nextPoints;
        }

        return leaderBoardScores;
    }

    public static List<LeaderBoardScore> createSortedLeaderBoardScoresWithoutRanks(int size, String country) {
        List<LeaderBoardScore> leaderBoardScores = new ArrayList<>();
        int previousPoints = random.nextInt(RANDOM_BOUND) + RANDOM_BOUND;
        for (int i = 0; i <size; i++) {
            int nextPoints = nextDescendingPoint(previousPoints);
            LeaderBoardScore leaderBoardScore = createLeaderBoardScoreWithoutRank(nextPoints, country);
            leaderBoardScores.add(leaderBoardScore);
            previousPoints = nextPoints;
        }

        return leaderBoardScores;
    }

    public static List<String> getLeaderBoardScoresCountries(List<LeaderBoardScore> leaderBoardScores) {
        return leaderBoardScores.stream().map(LeaderBoardScore::getUser).map(User::getCountry)
                        .collect(Collectors.toList());
    }

    public static List<Score> createRandomScoreList(int size) {
        List<Score> scores = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Score score = createRandomScore();
            scores.add(score);
        }

        return scores;
    }

    public static Score createRandomScore() {
        User user = createRandomUser();
        return Score.builder()
                .id(generateId())
                .timestamp(System.currentTimeMillis())
                .scoreValue(generateRandomScoreValue())
                .user(user)
                .build();
    }

    private static User createRandomUser() {
        return User.builder()
                .id(generateId())
                .name(generateRandomName())
                .surname(generateRandomSurname())
                .displayName(generateRandomDisplayName())
                .country(getRandomCountry())
                .build();
    }
}
