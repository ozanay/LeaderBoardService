package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.User;
import com.leaderboard.provider.persistence.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;

public class UserToUserEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User source) {
        return UserEntity.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .displayName(source.getDisplayName())
                .country(source.getCountry())
                .build();
    }
}
