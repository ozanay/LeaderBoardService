package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.User;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class ScoreToUserResourceConverter implements Converter<Score, UserResource> {
    private UserToUserResourceConverter userToUserResourceConverter;

    @Override
    public UserResource convert(Score source) {
        User user = source.getUser();
        return userToUserResourceConverter.convert(user);
    }
}
