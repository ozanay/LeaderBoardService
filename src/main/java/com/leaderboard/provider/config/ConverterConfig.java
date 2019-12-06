package com.leaderboard.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leaderboard.provider.converter.LeaderBoardPlayerToResourceConverter;

@Configuration
public class ConverterConfig {
    @Bean
    public LeaderBoardConversionService conversionService() {
        LeaderBoardConversionService conversionService = new LeaderBoardConversionService();
        conversionService.addConverter(new LeaderBoardPlayerToResourceConverter());
        
        return conversionService;
    }
}
