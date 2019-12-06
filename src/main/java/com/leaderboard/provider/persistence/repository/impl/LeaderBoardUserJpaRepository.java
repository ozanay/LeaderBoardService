package com.leaderboard.provider.persistence.repository.impl;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leaderboard.provider.persistence.entity.LeaderBoardEntity;

public interface LeaderBoardUserJpaRepository extends PagingAndSortingRepository<LeaderBoardEntity, String> {
    Iterable<LeaderBoardEntity> findAllByPlayer_Country(String country);
}
