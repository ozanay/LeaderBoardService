package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.User;
import com.leaderboard.provider.persistence.entity.ScoreEntity;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class ScoreEntityToScoreConverter implements Converter<ScoreEntity, Score> {
    private UserEntityToUserConverter userEntityToUserConverter;

    @Override
    public Score convert(ScoreEntity source) {
        User user = userEntityToUserConverter.convert(source.getUserEntity());
        return Score.builder()
                .id(source.getId())
                .scoreValue(source.getScoreValue())
                .timestamp(source.getTimestamp())
                .user(user)
                .build();
    }
}
