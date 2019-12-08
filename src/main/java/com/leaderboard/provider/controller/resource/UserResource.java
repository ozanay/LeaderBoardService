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
public class UserResource {
    @JsonProperty("user_id")
    private String id;
    @JsonProperty(required = true)
    private String displayName;
    @JsonProperty(required = true)
    private String country;

    private String name;
    private String surname;
}
