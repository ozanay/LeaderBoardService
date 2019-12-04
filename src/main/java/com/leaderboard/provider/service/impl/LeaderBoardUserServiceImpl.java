package com.leaderboard.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderboard.provider.model.User;
import com.leaderboard.provider.persistence.LeaderBoardUserRepository;
import com.leaderboard.provider.service.LeaderBoardUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LeaderBoardUserServiceImpl implements LeaderBoardUserService {
    private LeaderBoardUserRepository leaderBoardUserRepository;

    @Override
    public List<User> getGlobalUsers() {
        log.info("Getting global users...");
        List<User> users = leaderBoardUserRepository.getUsers();
        log.info("Getting global users finished successfully.");
        return users;
    }

    @Override
    public List<User> getCountryUsers(String countryIsoCode) {
        log.info("Getting country users...");
        List<User> users = leaderBoardUserRepository.getUsersByCountry(countryIsoCode);
        updateRanks(users);
        log.info("Getting country users finished successfully.");
        return users;
    }

    private void updateRanks(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            user.setRank(i + 1);
        }
    }
}
