package com.leaderboard.provider.converter;

import org.springframework.core.convert.converter.Converter;

import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.persistence.entity.ScoreEntity;
import com.leaderboard.provider.persistence.entity.UserEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScoreToScoreEntityConverter implements Converter<Score, ScoreEntity> {
    private UserToUserEntityConverter userToUserEntityConverter;

    @Override
    public ScoreEntity convert(Score source) {
        UserEntity userEntity = userToUserEntityConverter.convert(source.getUser());
        return ScoreEntity.builder()
                .id(source.getId())
                .scoreValue(source.getScoreValue())
                .timestamp(source.getTimestamp())
                .userEntity(userEntity)
                .build();
    }
}
