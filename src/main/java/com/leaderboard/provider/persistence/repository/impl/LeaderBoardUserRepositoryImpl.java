package com.leaderboard.provider.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leaderboard.provider.model.LeaderBoardPlayer;
import com.leaderboard.provider.persistence.entity.LeaderBoardEntity;
import com.leaderboard.provider.persistence.entity.PlayerEntity;
import com.leaderboard.provider.persistence.repository.LeaderBoardUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LeaderBoardUserRepositoryImpl implements LeaderBoardUserRepository {
    private LeaderBoardUserJpaRepository leaderBoardUserJpaRepository;

    @Override
    public List<LeaderBoardPlayer> getUsers() {
        log.info("Getting users from repo...");
        Iterable<LeaderBoardEntity> leaderboard = leaderBoardUserJpaRepository.findAll();
        List<LeaderBoardPlayer> leaderBoardPlayers = convert(leaderboard);
        log.info("Getting users from repo finished successfully.");
        return leaderBoardPlayers;
    }

    @Override
    public List<LeaderBoardPlayer> getUsersByCountry(String country) {
        log.info("Getting users of country <{}> from repo...", country);
        Iterable<LeaderBoardEntity> leaderboard = leaderBoardUserJpaRepository.findAllByPlayer_Country(country);
        List<LeaderBoardPlayer> leaderBoardPlayers = convert(leaderboard);
        log.info("Getting users of country <{}> from repo finished successfully.", country);
        return leaderBoardPlayers;
    }

    private static List<LeaderBoardPlayer> convert(Iterable<LeaderBoardEntity> entities) {
        List<LeaderBoardPlayer> leaderBoardPlayers = new ArrayList<>();
        int rank = 1;
        for (LeaderBoardEntity leaderBoardEntity : entities) {
            PlayerEntity playerEntity = leaderBoardEntity.getPlayer();
            LeaderBoardPlayer player = LeaderBoardPlayer.builder()
                    .id(playerEntity.getId())
                    .displayName(playerEntity.getCountry())
                    .country(playerEntity.getCountry())
                    .rank(rank)
                    .points(leaderBoardEntity.getPoints())
                    .build();
            leaderBoardPlayers.add(player);
            rank++;
        }

        return leaderBoardPlayers;
    }
}
