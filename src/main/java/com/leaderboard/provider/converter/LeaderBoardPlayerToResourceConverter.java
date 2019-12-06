package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.LeaderBoardPlayerResource;
import com.leaderboard.provider.model.LeaderBoardPlayer;
import org.springframework.core.convert.converter.Converter;

public class LeaderBoardPlayerToResourceConverter implements Converter<LeaderBoardPlayer, LeaderBoardPlayerResource> {
    @Override
    public LeaderBoardPlayerResource convert(LeaderBoardPlayer source) {
        if (source == null) {
            return null;
        }
        
        return LeaderBoardPlayerResource.builder()
            .rank(source.getRank())
            .points(source.getPoints())
            .displayName(source.getDisplayName())
            .country(source.getCountry())
            .build();
    }
}
