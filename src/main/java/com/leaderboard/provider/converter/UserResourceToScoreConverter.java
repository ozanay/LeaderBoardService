package com.leaderboard.provider.converter;

import org.springframework.core.convert.converter.Converter;

import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.model.Score;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserResourceToScoreConverter implements Converter<UserResource, Score> {
    private static final int INITIAL_SCORE_VALUE = 0;
    private UserResourceToUserConverter userResourceToUserConverter;

    @Override
    public Score convert(UserResource source) {
        return Score.builder()
                .scoreValue(INITIAL_SCORE_VALUE)
                .timestamp(System.currentTimeMillis())
                .user(userResourceToUserConverter.convert(source))
                .build();
    }
}
