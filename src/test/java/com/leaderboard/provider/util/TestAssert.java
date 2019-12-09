package com.leaderboard.provider.util;

import static org.testng.Assert.*;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaderboard.provider.controller.resource.UserResource;
import org.testng.Assert;

public final class TestAssert {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TestAssert() {}

    public static void assertPointsAreDescending(List<Double> userPoints) {
        for (int i = 0; i < userPoints.size(); i++) {
            int nextIndex = i + 1;
            if (nextIndex != userPoints.size()) {
                double current = userPoints.get(i);
                double next = userPoints.get(nextIndex);
                assertTrue(current >= next);
            }
        }
    }

    public static void assertRanksAreAscendingByOne(List<Integer> userRanks) {
        for (int i = 0; i < userRanks.size(); i++) {
            int expectedRank = i + 1;
            assertEquals(userRanks.get(i).intValue(), expectedRank);
        }
    }

    public static void assertBothListsContainSameUsers(List<UserResource> actualUsers,
                                                       List<UserResource> expectedUsers) {
        assertFalse(actualUsers.isEmpty());
        assertEquals(actualUsers.size(), expectedUsers.size());
        actualUsers.forEach(userResource -> {
            boolean isMatched = expectedUsers.stream().map(UserResource::getId).anyMatch(userResource.getId()::equals);
            assertTrue(isMatched);
        });
    }

    public static void assertScoresInTheSameCountry(String country, List<String> countries) {
        boolean isSameCountry = countries.stream().allMatch(country::equals);
        assertTrue(isSameCountry);
    }

    public static <T> void assertSame(T actualT, T expectedT) {
        try {
            String actual = objectMapper.writeValueAsString(actualT);
            String expected = objectMapper.writeValueAsString(expectedT);
            assertEquals(actual, expected);
        } catch (JsonProcessingException e) {
            Assert.fail("Converting to string failed.", e);
        }
    }

    public static <T> void assertSameList(List<T> actualTList, List<T> expectedTList) {
        assertEquals(actualTList.size(), expectedTList.size());
        for (int i = 0; i < actualTList.size(); i++) {
            assertSame(actualTList.get(i), expectedTList.get(i));
        }
    }
}
