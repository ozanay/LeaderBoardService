package com.leaderboard.provider.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScoreWorth {
    private String userId;
    private double value;
    private long timestamp;
}
