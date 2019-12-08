package com.leaderboard.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leaderboard.provider.converter.*;

@Configuration
public class ConverterConfig {
    @Bean
    public LeaderBoardConversionService conversionService() {
        LeaderBoardConversionService conversionService = new LeaderBoardConversionService();
        conversionService.addConverter(new LeaderBoardScoreToLeaderBoardResourceConverter());

        UserResourceToUserConverter userResourceToModelConverter = new UserResourceToUserConverter();
        conversionService.addConverter(userResourceToModelConverter);
        conversionService.addConverter(new UserResourceToScoreConverter(userResourceToModelConverter));

        UserToUserEntityConverter userToEntityConverter = new UserToUserEntityConverter();
        conversionService.addConverter(userToEntityConverter);
        conversionService.addConverter(new ScoreToScoreEntityConverter(userToEntityConverter));

        UserEntityToUserConverter userEntityToUserConverter = new UserEntityToUserConverter();
        conversionService.addConverter(userEntityToUserConverter);
        conversionService.addConverter(new ScoreEntityToScoreConverter(userEntityToUserConverter));

        UserToUserResourceConverter userToUserResourceConverter = new UserToUserResourceConverter();
        conversionService.addConverter(userToEntityConverter);
        conversionService.addConverter(new ScoreToUserResourceConverter(userToUserResourceConverter));

        conversionService.addConverter(new ScoreEntityToLeaderBoardScoreConverter(userEntityToUserConverter));
        conversionService.addConverter(new ScoreWorthResourceToScoreWorthConverter());

        conversionService.addConverter(new ScoreToScoreWorthConverter());
        conversionService.addConverter(new ScoreWorthToScoreWorthResourceConverter());
        return conversionService;
    }
}
