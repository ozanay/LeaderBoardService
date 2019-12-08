package com.leaderboard.provider.converter;

import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import org.springframework.core.convert.converter.Converter;

public class ScoreToScoreWorthConverter implements Converter<Score, ScoreWorth> {
    @Override
    public ScoreWorth convert(Score source) {
        return ScoreWorth.builder()
                .userId(source.getUser().getId())
                .value(source.getScoreValue())
                .timestamp(source.getTimestamp())
                .build();
    }
}
