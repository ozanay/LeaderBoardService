package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.User;
import org.springframework.core.convert.converter.Converter;

public class LeaderBoardScoreToLeaderBoardResourceConverter implements Converter<LeaderBoardScore, LeaderBoardResource> {
    @Override
    public LeaderBoardResource convert(LeaderBoardScore source) {
        User user = source.getUser();
        return LeaderBoardResource.builder()
                .rank(source.getRank())
                .points(source.getScoreValue())
                .displayName(user.getDisplayName())
                .country(user.getCountry())
                .build();
    }
}
