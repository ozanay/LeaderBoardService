package com.leaderboard.provider.service;

import java.util.List;

import com.leaderboard.provider.model.User;

/**
 * This service is responsible to provide leaderboard user operations.
 */
public interface LeaderBoardUserService {
    /**
     * @return Ordered global leaderboard users.
     */
    List<User> getGlobalUsers();

    /**
     *
     * @param countryIsoCode
     *            According to ISO 3166-1 alpha-2 standards.
     * @return Ordered country leaderboard users.
     */
    List<User> getCountryUsers(String countryIsoCode);
}
