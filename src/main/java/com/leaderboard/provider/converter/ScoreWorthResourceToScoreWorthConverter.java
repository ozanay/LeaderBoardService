package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.ScoreWorth;
import org.springframework.core.convert.converter.Converter;

import com.leaderboard.provider.controller.resource.ScoreWorthResource;

public class ScoreWorthResourceToScoreWorthConverter implements Converter<ScoreWorthResource, ScoreWorth> {

    @Override
    public ScoreWorth convert(ScoreWorthResource source) {
        return ScoreWorth.builder()
                .userId(source.getUserId())
                .value(source.getScoreWorth())
                .timestamp(source.getTimestamp())
                .build();
    }
}
