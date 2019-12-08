package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.exception.BadRequestException;
import com.leaderboard.provider.model.User;
import com.leaderboard.provider.util.IsoCountyUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Locale;

public class UserResourceToUserConverter implements Converter<UserResource, User> {
    @Override
    public User convert(UserResource source) {
        String country = source.getCountry().toUpperCase(Locale.getDefault());
        boolean isValid = IsoCountyUtil.isValidIsoCountryCode(country);
        if (!isValid) {
            throw new BadRequestException(String.format("Invalid country code: <%s> for user <%s>", country,
                            source.getDisplayName()));
        }

        return User.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .displayName(source.getDisplayName())
                .country(country)
                .build();
    }
}
