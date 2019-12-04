package com.leaderboard.provider.persistence;

import com.leaderboard.provider.model.User;

import java.util.List;

public interface LeaderBoardUserRepository {
    List<User> getUsers();

    List<User> getUsersByCountry(String countryIsoCode);
}
