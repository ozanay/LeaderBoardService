package com.leaderboard.provider.persistence.repository.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leaderboard.provider.persistence.entity.ScoreEntity;

public interface LeaderBoardScoreJpaRepository extends PagingAndSortingRepository<ScoreEntity, String> {
    Iterable<ScoreEntity> findByOrderByScoreValueDesc();

    Iterable<ScoreEntity> findAllByUserEntity_Country(String country, Sort sort);

    ScoreEntity findByUserEntity_Id(String userId);
}
