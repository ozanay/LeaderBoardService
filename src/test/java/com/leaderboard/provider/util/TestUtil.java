package com.leaderboard.provider.util;

import com.leaderboard.provider.config.ConverterConfig;
import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.model.User;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public final class TestUtil {
    private static final Random random = new SecureRandom();
    private static final LeaderBoardConversionService conversionService = new ConverterConfig().conversionService();
    
    private static final String DISPLAY_NAME_PREFIX = "PLAYER_";
    
    private TestUtil() {}
    
    public static List<User> getOrderedUsers() {
        User first = TestUtil.createUser(1, 100);
        User second = TestUtil.createUser(2, 99);
        User third = TestUtil.createUser(3, 98);
        
        return Arrays.asList(first, second, third);
    }
    
    private static User createUser(int rank, int points) {
        return User.builder()
            .id(generateId())
            .rank(rank)
            .points(points)
            .country(getRandomCountry())
            .displayName(generateRandomName())
            .build();
    }
    
    public static LeaderBoardConversionService conversionService() {
        return conversionService;
    }
    
    private static String generateId() {
        return UUID.randomUUID().toString();
    }
    
    private static String getRandomCountry() {
        String[] isoCountries = Locale.getISOCountries();
        int countryIndex = random.nextInt(isoCountries.length);
        return isoCountries[countryIndex];
    }
    
    private static String generateRandomName() {
        return DISPLAY_NAME_PREFIX + generateId();
    }
}
