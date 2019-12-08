package com.leaderboard.provider.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private String id;
    @Setter
    private double scoreValue;
    private long timestamp;
    private User user;
}
