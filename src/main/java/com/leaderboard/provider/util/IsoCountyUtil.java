package com.leaderboard.provider.util;

import java.util.Arrays;
import java.util.Locale;

public final class IsoCountyUtil {
    private IsoCountyUtil() {}

    public static boolean isValidIsoCountryCode(String isoCode) {
        return !StringUtil.isNullOrWhiteSpace(isoCode) && Arrays.asList(Locale.getISOCountries()).contains(isoCode);
    }
}
