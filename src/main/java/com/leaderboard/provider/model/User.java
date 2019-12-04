package com.leaderboard.provider.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private int rank;
    private int points;
    private String displayName;
    private String country;
}
