package com.leaderboard.provider.util;

import java.util.Arrays;
import java.util.Locale;

public final class IsoCountyUtil {
    private IsoCountyUtil() {}

    public static boolean isValidIsoCountryCode(String isoCode) {
        return !StringUtil.isNullOrWhiteSpace(isoCode)
                        && Arrays.stream(Locale.getISOCountries()).anyMatch(isoCode::equalsIgnoreCase);
    }
}
