package com.leaderboard.provider.service.impl;

import java.util.List;

import com.leaderboard.provider.model.LeaderBoardPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderboard.provider.persistence.repository.LeaderBoardUserRepository;
import com.leaderboard.provider.service.LeaderBoardUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LeaderBoardUserServiceImpl implements LeaderBoardUserService {
    private LeaderBoardUserRepository leaderBoardUserRepository;

    @Override
    public List<LeaderBoardPlayer> getGlobalUsers() {
        log.info("Getting global users...");
        List<LeaderBoardPlayer> leaderBoardPlayers = leaderBoardUserRepository.getUsers();
        log.info("Getting global users finished successfully.");
        return leaderBoardPlayers;
    }

    @Override
    public List<LeaderBoardPlayer> getCountryUsers(String countryIsoCode) {
        log.info("Getting country users...");
        List<LeaderBoardPlayer> leaderBoardPlayers = leaderBoardUserRepository.getUsersByCountry(countryIsoCode);
        updateRanks(leaderBoardPlayers);
        log.info("Getting country users finished successfully.");
        return leaderBoardPlayers;
    }

    private void updateRanks(List<LeaderBoardPlayer> leaderBoardPlayers) {
        for (int i = 0; i < leaderBoardPlayers.size(); i++) {
            LeaderBoardPlayer leaderBoardPlayer = leaderBoardPlayers.get(i);
            leaderBoardPlayer.setRank(i + 1);
        }
    }
}
