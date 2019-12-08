package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.model.User;
import org.springframework.core.convert.converter.Converter;

public class UserToUserResourceConverter implements Converter<User, UserResource> {
    @Override
    public UserResource convert(User source) {
        return UserResource.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .displayName(source.getDisplayName())
                .country(source.getCountry())
                .build();
    }
}
