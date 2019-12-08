package com.leaderboard.provider.converter;

import com.leaderboard.provider.controller.resource.ScoreWorthResource;
import com.leaderboard.provider.model.ScoreWorth;
import org.springframework.core.convert.converter.Converter;

public class ScoreWorthToScoreWorthResourceConverter implements Converter<ScoreWorth, ScoreWorthResource> {
    @Override
    public ScoreWorthResource convert(ScoreWorth source) {
        return ScoreWorthResource.builder()
                .userId(source.getUserId())
                .scoreWorth(source.getValue())
                .timestamp(source.getTimestamp())
                .build();
    }
}
