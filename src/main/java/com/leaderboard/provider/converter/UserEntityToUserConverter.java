package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.User;
import com.leaderboard.provider.persistence.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;

public class UserEntityToUserConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity source) {
        return User.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .displayName(source.getDisplayName())
                .country(source.getCountry())
                .build();
    }
}
