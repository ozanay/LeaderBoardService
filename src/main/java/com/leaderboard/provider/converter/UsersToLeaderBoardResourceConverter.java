package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.model.User;
import org.springframework.core.convert.converter.Converter;

public class UsersToLeaderBoardResourceConverter implements Converter<User, LeaderBoardResource> {
    @Override
    public LeaderBoardResource convert(User source) {
        if (source == null) {
            return null;
        }
        
        return LeaderBoardResource.builder()
            .rank(source.getRank())
            .points(source.getPoints())
            .displayName(source.getDisplayName())
            .country(source.getCountry())
            .build();
    }
}
