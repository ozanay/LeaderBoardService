package com.leaderboard.provider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String displayName;
    private String country;
    private String name;
    private String surname;
}
