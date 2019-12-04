package com.leaderboard.provider.util;

public final class StringUtil {
    private StringUtil() {}

    public static boolean isNullOrWhiteSpace(String str) {
        return str == null || str.trim().isEmpty();
    }
}
