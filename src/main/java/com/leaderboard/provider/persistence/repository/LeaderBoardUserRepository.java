package com.leaderboard.provider.persistence.repository;

import com.leaderboard.provider.model.LeaderBoardPlayer;

import java.util.List;

public interface LeaderBoardUserRepository {
    List<LeaderBoardPlayer> getUsers();

    List<LeaderBoardPlayer> getUsersByCountry(String countryIsoCode);
}
