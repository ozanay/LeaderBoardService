package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.User;
import com.leaderboard.provider.persistence.entity.ScoreEntity;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class ScoreEntityToLeaderBoardScoreConverter implements Converter<ScoreEntity, LeaderBoardScore> {
    private UserEntityToUserConverter userEntityToUserConverter;

    @Override
    public LeaderBoardScore convert(ScoreEntity source) {
        User user = userEntityToUserConverter.convert(source.getUserEntity());
        return LeaderBoardScore.leaderBoardScoreBuilder()
                .id(source.getId())
                .scoreValue(source.getScoreValue())
                .timestamp(source.getTimestamp())
                .user(user)
                .build();
    }
}
