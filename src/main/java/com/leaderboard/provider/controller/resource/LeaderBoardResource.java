package com.leaderboard.provider.controller.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoardResource {
    private int rank;
    private double points;
    @JsonProperty("display_name")
    private String displayName;
    private String country;
}
