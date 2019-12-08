package com.leaderboard.provider.controller.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreWorthResource {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("score_worth")
    private double scoreWorth;
    private long timestamp;
}
