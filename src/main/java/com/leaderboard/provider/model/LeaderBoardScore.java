package com.leaderboard.provider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LeaderBoardScore extends Score {
    private int rank;

    @Builder(builderMethodName = "leaderBoardScoreBuilder")
    public LeaderBoardScore(String id, double scoreValue, long timestamp, User user, int rank) {
        super(id, scoreValue, timestamp, user);
        this.rank = rank;
    }
}
